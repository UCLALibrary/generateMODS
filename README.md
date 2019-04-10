# GenerateMODS maven application

#### This OPENCSV project is used to create MODS from a spreadsheet for IDEP
#### The spreadsheet mapping is [here](https://docs.google.com/spreadsheets/d/1TTQKmFVWYumsWC5QwKf5E7bxzAmgnefOHrzAiCiQfrU/edit#gid=15338334)
#### This should not be used for  `Green Movement`
# Environment Setup - Windows 10 64-bit


## System Requirements

#### 1. Install Java 8

Go [here](https://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)


#### 2. Download and install Eclipse Java EE IDE for Web Developers IDE

Go [here](https://www.eclipse.org/downloads/download.php?file=/technology/epp/downloads/release/2019-03/R/eclipse-jee-2019-03-R-win32-x86_64.zip) 


## Get the Code

Clone the Git repo at `https://github.com/UCLALibrary/GenerateMODS.git`.

```Shell
cd eclipse-workspace
git clone https://github.com/UCLALibrary/GenerateMODS.git
cd GenerateMods
ls
``` 

## Project Setup

#### 1. Open the project

  Run Eclipse IDE  
    
  The working directory is the default `\eclipse-workspace`  
  Click `Launch`
  
##### 1.1 Open Project Wizard.

 Click `File Menu`
 Click  `Open Projects from the file system` 

  Next to Source file box click `directory`
  Select folder `GenerateMods`
  Click `Finish`

##### 1.2 Open OpenCSVidep.java file

+ On the left side , click the `Project Explorer` tab
+ You will see the `GenerateMods` projects in this window
+ uncollapse this projects and open `src\main\java` 
+ open package `edu.ucla.library.dep.GenerateMods`
+ double click `OpenCSVIdep.java`, this will open in the big window.

##### 1.3 Update the path to the spreadsheet and mods location

+ On the netapp the spreadsheet will be under DLIngest folder
+ create a `mods` folder where spreadsheet is located
+ goto line no 28 where you see the text `\\\\svm_dlib\\DLIngest\\embroideries\\embroideries-metadata.csv`
+ update with right file path and file name
+ goto line 713 and add path to idep mods on Netapp like `new FileWriter("\\\\svm_dlib\\DLIngest\\embroideries\\mods\\"`


#### 2 Run the java file.

+ right click anywhere in the code or the file name 'OpenCSVIdep'
+ Click 'Run as' menu from the drop down and select 'Java application'
+ In the console window at the botto of the IDE you will see some logging , if any error contact the developer
+ Most of errors could be related to file path or the driectory does not exists.

#### Open the mods folder on Netapp

Verify and QA The mods xml metadata, sometimes the file names in the spreadsheet do not match with file names of islandora objects, so the file names needs to be fixed in the csv and then mods needs to recreated.

