# This program demos how to connect to Milvus vector database,
# create a vector collection,
# insert 10 vectors,
# and execute a vector similarity search.

import pandas
from milvus import Milvus, IndexType, MetricType

# Milvus server IP address and port.
# You may need to change _HOST and _PORT accordingly.
_HOST = '127.0.0.1'
_PORT = '19530'  # default value
# _PORT = '19121'  # default http value

# Vector parameters
_DIM = 10  # dimension of vector

_INDEX_FILE_SIZE = 32  # max file size of stored index

# 将逗号分割的向量字符串转换为List的函数


def str_to_list(vector_str):
    return list(map(float, vector_str.split(',')))


def read_csv(path):
    item_embedding = pandas.read_csv(path)
    return item_embedding['book_id'].to_list(), item_embedding['vector'].apply(str_to_list).to_list()


def main():
    # Specify server addr when create milvus client instance
    # milvus client instance maintain a connection pool, param
    # `pool_size` specify the max connection num.
    milvus = Milvus(_HOST, _PORT)

    # get vectors
    # element per dimension is float32 type
    # vectors should be a 2-D array
    ids, vectors = read_csv('item_embedding.csv')
    # print(vectors)

    if (len(vectors) == 0):
        print("Vector list is empty")
    else:
        _DIM = len(vectors[0])

    # Create collection if it dosen't exist.
    collection_name = 'item_embedding'

    status, ok = milvus.has_collection(collection_name)
    if not ok:
        param = {
            'collection_name': collection_name,
            'dimension': _DIM,
            'index_file_size': _INDEX_FILE_SIZE,  # optional
            'metric_type': MetricType.L2  # optional
        }

        milvus.create_collection(param)

    # Show collections in Milvus server
    _, collections = milvus.list_collections()

    # Describe demo_collection
    _, collection = milvus.get_collection_info(collection_name)
    print(collection)

    # Insert vectors into collection, return status and vectors id list
    status, ids = milvus.insert(
        collection_name=collection_name, records=vectors, ids=ids)
    if not status.OK():
        print("Insert failed: {}".format(status))

    # Flush collection  inserted data to disk.
    milvus.flush([collection_name])
    # Get demo_collection row count
    status, result = milvus.count_entities(collection_name)

    # present collection statistics info
    _, info = milvus.get_collection_stats(collection_name)
    print(info)

    # Obtain raw vectors by providing vector ids
    status, result_vectors = milvus.get_entity_by_id(collection_name, ids[:10])

    # create index of vectors, search more rapidly
    index_param = {
        'nlist': 2048
    }

    # Create ivflat index in demo_collection
    # You can search vectors without creating index. however, Creating index help to
    # search faster
    print("Creating index: {}".format(index_param))
    status = milvus.create_index(
        collection_name, IndexType.IVF_FLAT, index_param)

    # describe index, get information of index
    status, index = milvus.get_index_info(collection_name)
    print(index)

    # Use the top 10 vectors for similarity search
    query_vectors = vectors[0:10]

    # execute vector similarity search
    search_param = {
        "nprobe": 16
    }

    print("Searching ... ")

    param = {
        'collection_name': collection_name,
        'query_records': query_vectors,
        'top_k': 1,
        'params': search_param,
    }

    status, results = milvus.search(**param)
    if status.OK():
        # indicate search result
        # also use by:
        #   `results.distance_array[0][0] == 0.0 or results.id_array[0][0] == ids[0]`
        if results[0][0].distance == 0.0 or results[0][0].id == ids[0]:
            print('Query result is correct')
        else:
            print('Query result isn\'t correct')

        # print results
        print(results)
    else:
        print("Search failed. ", status)


if __name__ == '__main__':
    main()