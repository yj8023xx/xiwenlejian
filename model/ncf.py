import torch
from torch import nn

def create_feature_map():
    feature_map = {
        "dataset_id": "bookcrossing",
        "num_fields": 2,
        "feature_specs": {
            "user_id": {
                "source": "user",
                "type": "categorical",
                "vocab_size": 278855,
                "index": 0
            },
            "book_id": {
                "source": "item",
                "type": "categorical",
                "vocab_size": 271361,
                "index": 1
            }
        }
    }
    return feature_map

class NCF(nn.Module):
    def __init__(self,
                 feature_map=None,
                 embedding_dim=10,
                 hidden_units=[256, 128, 64]):
        super(NCF, self).__init__()
        self.feature_map = feature_map
        if self.feature_map is None:
            self.feature_map = create_feature_map()
        # Embedding
        self.embedding = nn.ModuleDict()
        for feature, feature_spec in self.feature_map['feature_specs'].items():
            if feature_spec['type'] == 'numerical':
                self.embedding[feature] = nn.Linear(
                    1, embedding_dim, bias=False)
            elif feature_spec['type'] == 'categorical':
                padding_idx = feature_spec.get('padding_idx', None)
                self.embedding[feature] = nn.Embedding(feature_spec['vocab_size'],
                                                       embedding_dim,
                                                       padding_idx=padding_idx)
        # DNN
        input_dim = self.feature_map['num_fields'] * embedding_dim
        hidden_units = [input_dim] + hidden_units
        hidden_layers = []
        for i in range(len(hidden_units) - 1):
            hidden_layers.append(nn.Linear(hidden_units[i], hidden_units[i + 1]))
            hidden_layers.append(nn.ReLU())
        hidden_layers.append(nn.Linear(hidden_units[-1], 1))
        self.dnn = nn.Sequential(*hidden_layers)
        # Sigmoid
        self.output_activation = nn.Sigmoid()

    def forward(self, X):
        feature_emb_list = []
        for feature, feature_spec in self.feature_map['feature_specs'].items():
            if feature_spec['type'] == 'numerical':
                raw_feature = X[:, feature_spec['index']].float().view(-1, 1)
            elif feature_spec['type'] == 'categorical':
                raw_feature = X[:, feature_spec['index']].long()
            embedding_vec = self.embedding[feature](raw_feature)
            feature_emb_list.append(embedding_vec)
        feature_emb = torch.stack(feature_emb_list, dim=1)
        out = self.dnn(feature_emb.flatten(start_dim=1))
        y_pred = self.output_activation(out).squeeze(1)
        return y_pred
