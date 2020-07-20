### Read logs app

## 1. Description of project
App reads logs and provide the following information:
- Name of service
- Number of requests made to the service
- Maximum time of request execution

Format of logs:  
2015-10-28T12:24:33,803 TRACE [OperImpl] entry with (addClient:97900)  
2015-10-28T12:24:34,002 TRACE [OperImpl] entry with (addClient:97900)  

Source file of logs: https://raw.githubusercontent.com/alexwaw/readlogs/master/test.log

## 2. Running app
- Running from IDE by executing Main.
- Running from docker: https://hub.docker.com/r/alexdr13/readapp/tags by executing command "docker pull alexdr13/readapp:latest"

## 3. Example of execution:

Source file:  
2015-10-28T12:24:33,803 TRACE [OperImpl] entry with (addClient:97900)  
2015-10-28T12:24:34,002 TRACE [OperImpl] entry with (addClient:97900)  
2015-10-28T12:24:33,703 TRACE [OperImpl] entry with (addClient:97901)  
2015-10-28T12:24:34,002 TRACE [OperImpl] entry with (addClient:97901)  
2015-10-28T12:24:33,901 TRACE [OperImpl] entry with (deleteClient:97902)  
2015-10-28T12:24:34,003 TRACE [OperImpl] entry with (deleteClient:97902)  
2015-10-28T12:24:33,900 TRACE [OperImpl] entry with (addClient:97103)  
2015-10-28T12:24:34,100 TRACE [OperImpl] entry with (addClient:97103)  

Run in Intellij:
![alt text](https://github.com/alexwaw/readlogs/blob/master/intellij.png?raw=true)

Run in Docker:
![alt text](https://github.com/alexwaw/readlogs/blob/master/docker.png?raw=true)
