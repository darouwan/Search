Sample queries for sampleData2:

============================
Query1:
java Circle_Test <Your index folder> 0.4 16 39 3 food pizza bar

Expected Output:
8.txt 0.59011096
6.txt 0.4674462
57.txt 0.387366
13.txt 0.28263947
40.txt 0.21362056

============================
Query2:
java Circle_Test <Your index folder> 0.5 14 40 5 fuel agip

Expected Output:
59.txt 1.4599824
6.txt 0.77920127

============================
Query3:
java Circle_Test <Your index folder> 0.6 8 44 2 parking parcheggio

Expected Output:
93.txt 1.4519837
86.txt 0.67048144
94.txt 0.6581161

============================
Query4:
java Rectangle_Test <Your index folder> 0.6 8 44 4 4 parking parcheggio

Expected Output:
93.txt 1.81043
86.txt 0.73693436
94.txt 0.7281908
90.txt 0.6234405

============================
Query5:
java Rectangle_Test <Your index folder> 0.4 16 39 4 4 food pizza bar

Expected Output:
6.txt 0.75006354
8.txt 0.7291377
13.txt 0.40333754
40.txt 0.32319814

============================
Query6:
java Rectangle_Test <Your index folder> 0.5 14 40 10 10 fuel agip

Expected Output:
59.txt 1.6727276
6.txt 0.84886163
73.txt 0.52565706
63.txt 0.471283

========================================================================
More debug info:

Query1:
Doc		textualScore    spatialScore
6.txt	0.5563025		0.40820867
8.txt	0.8573325		0.41196322
13.txt	0.07918126		0.41827828
40.txt	0.07918126		0.30324674
57.txt	0.5563025		0.27474165

Query2:
Doc		textualScore    spatialScore
6.txt	1.09691			0.46149254
59.txt	2.49485			0.42511475

Query3:
Doc		textualScore    spatialScore
86.txt	0.845098		0.4085564
93.txt	2.1673174		0.37898314
94.txt	0.845098		0.37764305

Query4:
Doc		textualScore    spatialScore
86.txt 	0.8403665 		0.5817862
90.txt 	0.94793254 		0.13670236
93.txt 	2.643467 		0.56087476
94.txt 	0.8403665 		0.55992717

Query5:
Doc		textualScore    spatialScore
6.txt 	1.316693 		0.37231052
8.txt 	1.258405 		0.37629282
13.txt 	0.43385744 		0.38299096
40.txt 	0.41652304 		0.26098156

Query6:
Doc		textualScore    spatialScore
6.txt 	1.0785055 		0.61921775
59.txt 	2.7519605 		0.5934948
63.txt 	0.8791699 		0.063396096
73.txt 	0.8791699 		0.1721443

========================================================================
Note that there might be some errors in the scores. It is because of different functions that you use to calculate them. So in these testcases (and also in the project marking), we will accept errors less than 0.00001.