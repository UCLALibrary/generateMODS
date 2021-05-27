# generateMODS is maven application

#### [openCSV](http://opencsv.sourceforge.net/#reading) library is used to create MODS from the spreadsheet for IDEP and Meap
#### The spreadsheet mapping is [here](https://docs.google.com/spreadsheets/d/1TTQKmFVWYumsWC5QwKf5E7bxzAmgnefOHrzAiCiQfrU/edit#gid=15338334)

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
## THE UPDATED WAY TO RUN THIS PROJECT
```Shell
git pull
PS \eclipse-workspace\GenerateMODS\target> java -jar .\GenerateMods-0.0.1-SNAPSHOT-jar-with-dependencies.jar 
Enter InputFilePath is: 
/Users/testuser/Downloads/barb_eph_cicz_Finalv6.csv
InputFilePath is: /Users/testuser/Downloads/barb_eph_cicz_Finalv6.csv
Enter OutputFilePath is: 
/Users/testuser/Downloads/mods/
OutputFilePath is: /Users/testuser/Downloads/mods/
Enter Enter program (IDEP or MEAP): 
MEAP
Enter program (IDEP or MEAP): MEAP
Program is: MEAP
Enter Project name is: 
GOOGLW
Project name is: sample
Enter Project URL is: 
google.com
Project URL is: sample.com
```
## Project Setup Current to build a jar file using maven

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
 
 #### Create a branch and make any changes to code as per the ticket
 
 #### Generate a jar file with dependencies
 
  Right click the pom.xml
  
  Click `Run As` in the list
  
  Click `maven install`
  
  Test the app with the instruction above.

#### Next commit the changes to git  

  Create PR for this change.
  
  Review and Merge the PR.
  
  Inform the stakeholders.
  

