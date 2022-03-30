So that you guys know what you're looking at..

I created a Java file that takes the input from AllData.txt and converts them into INSERT statements.
The result.txt is what the program currently prints. I still need to format it a bit better, because some input requires the '' symbols, and right now it does not add the strings to the database, only prints them on the command line. This is because we will need to update some of our .sql file to ensure that entities and tables reflect the data input exactly. For most of the data, I took the variable names directly from the AllData.txt file, which is why there may be contrasting data. It wasn't intentional, it was just easier to code with the AllData.txt file. Once we do update the sql file, we can add the calls to st.executeUpdate(string) as reflected in the Java demo file JavaDemoTransaction anywhere they are currently being printed by the program. 

In order to run the java program, you will need a folder with the program, the jar file and AllData.txt. You may need to add whatever .jar file works for your system. I am running 1.8 on Windows, so I use postgresql-42.2.18.jar. You will also need to update the **addyourpasswordhere!** line in InsertData.java to reflect your local database's password. I found that going through the JavaDemos information listed on the project page helped a lot for this step.

I was able to run the program using the commands:<br>
  ```javac -cp "postgresql-42.2.18.jar;." InsertData.java``` <br>
  ```java -cp "postgresql-42.2.18.jar;." InsertData```

It's a bit of a mess, so if you notice any coding logic that could be better achieved using another method, feel free to update! I just wanted to get the basic INSERT statements out of the way.

Thanks!! <3
