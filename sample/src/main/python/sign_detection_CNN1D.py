def conv1D_model(segno):
    from numpy import array, nditer
    from tensorflow.python.keras.models import load_model
    from os.path import dirname, join
    import pandas as pd
    #filename = join(dirname(__file__), 'model_cnn.h5')
    #dataset_path = join(dirname(__file__), 'Dataset_segni.csv')
    #filename = join(dirname(__file__), 'saved_model')
    #load_model = load_model(filename)
    load_model = load_model('/Users/Mery/Desktop/python file/model_better')
    x_input = array(segno)

    df = pd.read_csv('/Users/Mery/Desktop/python file/Lis_Trad_dinamico_dx(2).csv')
    trad = df["Traduzione"]

    gest = []
    gest = gest + [trad[0]]
    num = 0
    numTrad = 0

        check = False

    for x in trad:
        for i in gest:
            if i == x:
                check = True
        if check == False:
            if x != gest[num]:
                num = num + 1
                gest = gest + [trad[numTrad]]
        numTrad = numTrad + 1
        check  = False 

    for x in gest:
        print(x)
    x_input = x_input.reshape((1, 130, 1))
    yhat = load_model.predict(x_input, verbose=0)
    mx = yhat[0][0]
    pos = 0 
    for x in nditer(yhat):
        if mx < x:
            mx = x
            pos_max = pos
        pos = pos + 1
        
    return gest[pos_max]
#print(conv1D_model([[28,100,67,66,40,0,0,1,1,0,59,224,104,25,96,64,63,31,0,0,1,1,0,58,225,102,39,96,66,65,25,0,0,0,0,0,70,219,133,0,19,6,9,0,0,0,0,0,0,27,136,58,0,3,0,0,0,0,0,0,0,0,30,134,4,31,44,48,32,6,0,0,0,0,0,-57,87,113,174,180,180,180,172,0,0,0,0,0,67,214,101,48,4,0,1,0,0,0,0,0,0,195,35,306,142,160,174,168,126,0,0,0,0,0,74,248,118,75,38,16,19,10,0,0,0,0,0,177,93,273]]))
