Color Streams
======================

#### Problem

There are two separate infinite sources of data.

Data is arriving "simultaneously" into our system as two streams via two channels: Channel 1 and Channel 2.

The data could be of three types: R, G and B.

Each data element should have two properties: channelNumber and uniqueID.

These three types of Data are arriving in a random sequence on the two channels.

Write a program which creates pairs of "same types" arriving on two channels in their "order of arrival".

For example, if a sample sequence is as follows:
Channel 1: R1_1 R1_2 R1_3 B1_4 B1_8 G1_5
Channel 2: B2_6 B2_8 R2_9 G2_10 B2_7 R2_20

Then the expected output is:
(R1_1, R2_9) (B1_4, B2_6) (B1_8, B2_8) (G1_5, G2_10) (R1_2, R2_20)

#### Solution Note

1. To execute the program do 'sbt run'
2. To try and simulate the infinite streams the program provides the 
user to enter data. Data entry happens in two steps. First enter the 
channel number followed by the data for that channel (refer to sample 
interaction below)
3. Receiving of the incoming input data and the compute to find the tuples
can be parallelized. The compute() function in the code can actually be 
called via a timer to do periodic compute of the tuples. Incoming data 
is stored in two channel vectors and each compute() removes that data that 
has a matching tuple. So the two channel vectors are always lightweight. 
The result vector can be flused to a database after every compute() if the 
incoming rate is high (in which case the periodic compute() call has be quite
frequent also)
4. There are two simple unit tests in the code. One checks for the exact 
result for the example in the problem. Another does iterative compute() 
for the same data to check that compute() can be called periodically and 
doing so does not break the logic
5. Sample interaction:

$sbt run
[info] Running in.bharathwrites.ColorStreams
Enter:
**1** or **2** for **Channel Number** and hit enter
**8** to print **results** and go back to **feeding* more data
**9** to print **results** and **end** the program:
1
Enter **data** and hit enter (use space as delimiter to enter multiple data elements at once):
R1_1 R1_2 R1_3 B1_4 B1_8 G1_5
Enter:
**1** or **2** for **Channel Number** and hit enter
**8** to print **results** and go back to **feeding* more data
**9** to print **results** and **end** the program:
2
Enter **data** and hit enter (use space as delimiter to enter multiple data elements at once):
B2_6 B2_8 R2_9 G2_10 B2_7 R2_20
Enter:
**1** or **2** for **Channel Number** and hit enter
**8** to print **results** and go back to **feeding* more data
**9** to print **results** and **end** the program:
9
**Result: Vector((R1_1,R2_9), (R1_2,R2_20), (B1_4,B2_6), (B1_8,B2_8), (G1_5,G2_10))**
[success] Total time: 16 s, completed 7 May, 2016 12:09:05 AM
