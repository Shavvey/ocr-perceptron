# OCR Perceptron

## Description
OCR (Optical Character Recognition) is a technique that a program can 
use to parse individual or stream images into a matched set of some written alphabet; 
often just a set of alphanumeric characters. OCR is used in banking, 
note taking applications, and many other services used on a daily basis. 
One of the most common implementations of OCR is the use of neural networks, 
often through supervised learning methods. This project aims to create a
really simple framework to test and train different implementations of fully connected
Neural Networks for the purposes of optical character recognition.


![Example Image](images/example.png)

## Compiling the Project
Once you have cloned the main repository, navigate
to the source code directory called `ocr-perceptron`.
From there, you can access a few gradle tasks that
will build aspects of the project.

- To run the GUI, you can use the command:
```./gradlew gui```


- To run a training example with a default
configuration of the network, use the command:
```./gradlew run```

- There is also a pre-compiled jar named `ocr-perceptron-1.0-BUILD.jar`
with the necessary class files to run the GUI. To launch the jar,
use the command ```java -jar ocr-perceptron-1.0-BUILD.jar```

