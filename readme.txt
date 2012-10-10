To test your code, firstly run the Spatial_Indexer, as below:

java Spatial_Indexer <Path of sampleData1 folder> <Your index folder> <Path of sampleLoc1.txt>

============================

Then you can run the two queries as below (we also offer the expected output):

Query1: 
java Rectangle_Test <Your index folder> 0.6 25 27 50 50 food fuel

Expected Output:
3.txt 0.797248
5.txt 0.75548947
4.txt 0.736111
8.txt 0.7170738
7.txt 0.7051842
2.txt 0.6868683
0.txt 0.65663785
1.txt 0.63271433

============================
Query2: 
java Rectangle_Test <Your index folder> 0.3 33 15 100 100 food

Expected Output:
3.txt 0.8682884
5.txt 0.8317698
4.txt 0.81541693

============================
Query3:
java Circle_Test <Your index folder> 0.6 25 27 50 food fuel

Expected Output:
3.txt 0.61003447
5.txt 0.5805067
4.txt 0.566804
2.txt 0.452431
8.txt 0.44882378
7.txt 0.44041657
1.txt 0.43910408
0.txt 0.43105483

============================
Query4:
java Circle_Test <Your index folder> 0.4 15 17 40 food parking

Expected Output:
3.txt 0.4582448
10.txt 0.41923362
5.txt 0.41215527
9.txt 0.4075998
4.txt 0.40506062
11.txt 0.40173206

========================================================================
Note that there might be some errors in the scores. It is because of different functions that you use to calculate them. So in these testcases (and also in the project marking), we will accept errors less than 0.00001.

========================================================================
More debug info:

Query1:
Doc		textualScore    spatialScore
0.txt 	0.8922327		0.30324548
1.txt 	0.8333879		0.3317039
2.txt 	0.8922327		0.37882173
3.txt 	1.0184656		0.46542162
4.txt 	1.0184656		0.31257898
5.txt 	1.0184656		0.3610252
7.txt 	0.95107746		0.33634418
8.txt 	0.95107746		0.36606818

Query2:
Doc		textualScore    spatialScore
3.txt	1.6604793		0.52877796
4.txt 	1.6604793		0.4532473
5.txt 	1.6604793		0.47660857

Query3:
Doc		textualScore    spatialScore
0.txt	0.38021126		0.50732017
1.txt	0.38021126		0.5274433
2.txt	0.38021126		0.5607606
3.txt	0.60206			0.62199605
4.txt	0.60206			0.51391995
5.txt	0.60206			0.54817665
7.txt	0.38021126		0.5307245
8.txt	0.38021126		0.55174255

Query4:
Doc		textualScore    spatialScore
3.txt	0.60206			0.362368
4.txt	0.60206			0.27372766
5.txt	0.60206			0.28555208
9.txt	0.60206			0.27795964
10.txt	0.60206			0.29734933
11.txt	0.60206			0.26818007