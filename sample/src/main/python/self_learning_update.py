def model_self_learning(java_matrix, parola_segno):
    import numpy as np
    import pandas as pd
    from csv import writer
    from pandas import read_csv
    from tensorflow.keras.models import Sequential
    from tensorflow.keras.layers import Dense
    from tensorflow.keras.layers import Flatten
    from tensorflow.keras.layers import Dropout
    from tensorflow.python.keras.layers.convolutional import Conv1D
    from tensorflow.python.keras.layers.convolutional import MaxPooling1D
    from tensorflow.keras.models import Model
    from tensorflow.python.keras.layers.advanced_activations import LeakyReLU
    from tensorflow.keras.utils import to_categorical
    from tensorflow.keras.models import model_from_json
    from os.path import dirname, join


    python_matrix = [[]]
    row = 0
    for x in java_matrix:
        if(row==0):
            python_matrix = [x]
        else:
            python_matrix = python_matrix + [x]
        row = row + 1

                
    i = 0
    dataset_path = join(dirname(__file__), 'Dataset_segni.csv')
    with open(dataset_path, 'a+',newline='') as write_obj:
      csv_writer = writer(write_obj)
      for x in python_matrix:
        python_matrix[i] = python_matrix[i] + [parola_segno]
        csv_writer.writerow(python_matrix[i])
        i = i + 1
    write_obj.close()

    df = pd.read_csv(dataset_path)

    trad = df["Traduzione"]
    trad.head()

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

    i = 0
    for x in gest:
      df.loc[df["Traduzione"] == gest[i], "label"] = i
      i = i + 1
    

    y = df.iloc[:,-1]

    print(y)

    x = df.iloc[:,:-2]

    print(x)

    y = to_categorical(y)

    x = np.array(x[:])
    print(x)

    from sklearn.model_selection import train_test_split
    x_train, x_test, y_train, y_test = train_test_split(x, y, test_size = 0.3, random_state = 120)

    x_train = x_train.reshape(x_train.shape[0],x_train.shape[1], 1)
    x_test = x_test.reshape(x_test.shape[0],x_test.shape[1], 1)

    batch_size = 64
    num_classes = len(gest)
    epochs = 10
    input_shape=(x_train.shape[1], 1)


    model = Sequential()
    model.add(Conv1D(filters=32, kernel_size=3,activation='linear',input_shape=input_shape))
    model.add(LeakyReLU(alpha=0.1))
    model.add(MaxPooling1D(pool_size=(2),padding='same'))
    model.add(Conv1D(filters=64, kernel_size=3,activation='linear',padding='same'))
    model.add(LeakyReLU(alpha=0.1))
    model.add(MaxPooling1D(pool_size=(2),padding='same'))
    model.add(Conv1D(filters=128, kernel_size=3, activation='linear',padding='same'))
    model.add(LeakyReLU(alpha=0.1))                  
    model.add(MaxPooling1D(pool_size=(2),padding='same'))
    model.add(Flatten()) 
    model.add(Dense(128, activation='linear'))
    model.add(LeakyReLU(alpha=0.1))                 
    model.add(Dense(num_classes, activation='softmax'))


    model.summary()
    model.compile(optimizer='adam', loss='categorical_crossentropy', metrics=['accuracy'])
    # fit model
    history = model.fit(x_train, y_train, batch_size=batch_size, epochs=10, verbose=1, validation_data=(x_test, y_test))

    model_path = join(dirname(__file__), 'model_cnn.h5')
    model.save(model_path)

    return python_matrix
#print(model_self_learning([[28,100,67,66,40,0,0,1,1,0,59,224,104,25,96,64,63,31,0,0,1,1,0,58,225,102,39,96,66,65,25,0,0,0,0,0,70,219,133,0,19,6,9,0,0,0,0,0,0,27,136,58,0,3,0,0,0,0,0,0,0,0,30,134,4,31,44,48,32,6,0,0,0,0,0,-57,87,113,174,180,180,180,172,0,0,0,0,0,67,214,101,48,4,0,1,0,0,0,0,0,0,195,35,306,142,160,174,168,126,0,0,0,0,0,74,248,118,75,38,16,19,10,0,0,0,0,0,177,93,273],[94,70,56,51,16,0,0,0,0,0,241,127,174,99,72,67,64,27,0,0,0,0,0,243,125,174,93,71,73,72,31,0,0,0,0,0,227,127,168,81,89,104,98,33,0,0,0,0,0,331,93,195,77,93,97,92,33,0,0,0,0,0,164,138,171,82,90,93,83,34,0,0,0,0,0,7,127,133,74,81,85,77,6,0,0,0,0,0,-98,116,129,75,93,87,78,12,0,0,0,0,0,-22,120,140,81,84,87,78,31,0,0,0,0,0,183,139,130,76,77,76,69,19,0,0,0,0,0,313,110,169]], 'Prosciutto'))
#
