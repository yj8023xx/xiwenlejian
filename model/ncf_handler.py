"""
Handler for NCF based recommendation system
"""
import json
import os
import torch
from ts.torch_handler.base_handler import BaseHandler

class NCFHandler(BaseHandler):
    """
    Handler for NCF
    """

    def initialize(self, context):
        """
        Initialize function loads the model.pt file and initialized the model object.
           This version creates and initialized the model on cpu fist and transfers to gpu in a second step to prevent GPU OOM.

        Args:
            context (context): It is a JSON Object containing information
            pertaining to the model artifacts parameters.
        Raises:
            RuntimeError: Raises the Runtime error when the model.py is missing
        """
        properties = context.system_properties

        # Set device to cpu to prevent GPU OOM errors
        self.device = 'cpu'
        self.manifest = context.manifest

        model_dir = properties.get('model_dir')
        model_pt_path = None
        if 'serializedFile' in self.manifest['model']:
            serialized_file = self.manifest['model']['serializedFile']
            model_pt_path = os.path.join(model_dir, serialized_file)

        # model def file
        model_file = self.manifest['model'].get('modelFile', '')

        if not model_file:
            raise RuntimeError('model.py not specified')

        self.model = self._load_pickled_model(model_dir, model_file, model_pt_path)

        self.map_location = (
            'cuda'
            if torch.cuda.is_available() and properties.get('gpu_id') is not None
            else 'cpu'
        )

        self.device = torch.device(
            self.map_location + ':' + str(properties.get('gpu_id'))
            if torch.cuda.is_available() and properties.get('gpu_id') is not None
            else self.map_location
        )

        self.model.to(self.device)

        self.model.eval()

        self.initialized = True

    def preprocess(self, data):
        """
        Transform raw input into model input data.

        Args:
            data (str): The input data is in the form of a string
        Returns:
            (Torch Tensor): categorical features
        """
        X = []
        for row in data:

            input = row.get('data') or row.get('body')

            if not isinstance(input, dict):
                input = json.loads(input)

            for item in input['data']:
                X.append([item['user_id'], item['book_id']])

        return torch.LongTensor(X)

    def inference(self, data):
        """
        The inference call moves the elements of the tuple onto the device and calls the model

        Args:
            data (torch tensor): The data is in the form of Torch Tensor
                                 whose shape should match that of the
                                  Model Input shape.

        Returns:
            (Torch Tensor): The predicted response from the model is returned
                            in this function.
        """
        with torch.no_grad():
            results = self.model(data)
        return results

    def postprocess(self, data):
        """
        The post process function converts the prediction response into a
        Torchserve compatible format

        Args:
            data (Torch Tensor): The data parameter comes from the prediction output
            output_explain (None): Defaults to None.

        Returns:
            (list): Returns the response containing the predictions which consist of a single score per input entry.
        """
        ret = []
        ret.append(data.tolist())
        return ret