# GenerateMODS maven application

#### This repository should remain private for the time being

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




Ensure that users have write access to these files:
+ `j2ee/home/application-deployments`
+ `j2ee/home/applications`
+ `j2ee/home/config`

Change the following variables in `/opt/jdeveloper/jdev/bin/jdev.conf`:

variable | value
--- | ---
`SetJavaHome` | `/usr/lib/jvm/java-6-openjdk-amd64` (may be different if you didn't use `apt-get`)
`AddVMOption` | `-Xmx1488M`
`AddVMOption` | `-XX:MaxPermSize=400M`

Comment out the following line in `jdev.conf`:

    SetJavaVM hotspot
    
    


#### 3. Update old libraries

Replace the folder `/opt/jdeveloper/jakarta-struts` with `DLCS-Staff/JDeveloperLibs/jakarta-struts`:

```Shell
rm -rf /opt/jdeveloper/jakarta-struts
cp -r DLCS-Staff/JDeveloperLibs/jakarta-struts /opt/jdeveloper/jakarta-struts
``` 

Replace the folder `/opt/jdeveloper/ord/jlib` with `DLCS-Staff/JDeveloperLibs/ord/jlib`:

```Shell
rm -rf /opt/jdeveloper/ord/jlib
cp -r DLCS-Staff/JDeveloperLibs/ord/jlib /opt/jdeveloper/ord/jlib
``` 

## Project Setup

#### 1. Open the project

Run the JDeveloper IDE:

```Shell
/opt/jdeveloper/jdev/bin/jdev
```

In the main menu, click on `File > Open` and select `DLCS Staff/DLCS Staff.jws`, click `Yes` to get through the dialog boxes.

#### 2. Setup JDK version 6

In the main menu, click on `Tools > Default Project Properties`, a series of dialog boxes follow:

*Default Project Properties*: in the left panel menu, select `Libraries`. If the form field contains a version of Java 6, skip to **_3. Database Connection Setup_**. Otherwise, click `Change`

*Edit J2SE Definition*: in the bottom left corner, click `New`

*Create J2SE*: click `Browse`

*Select Java Executable*: select the Java 6 binary (should be in `/usr/lib/jvm/java-6-openjdk-amd64/bin/java`), click `Open`

click `OK` to close the dialog boxes.

#### 3. Database connection setup

In the Application Navigator panel, find `Constants.java` in `DLCS Staff > Model > Application Sources > edu.ucla.library > digital > dlcs > util`. Change the values of `CONNECTION_URL` (replace every instance of the substring `'106'` with `'107'`) and `DB_PASSWORD` (ask repo maintainers). `Save` this file.

In the main menu, click on `View > Structure`. There should now be a new window in the workspace called `Structure`.

In the Application Navigator panel, select `sessions.xml` under `DLCS Staff > Model > Application Sources > TopLink`. In the `Structure` window, double click the child folder called `dlcssession`. Under the `Login` tab, change `Encrypted Password` and `Connection URL` to the same values as `DB_PASSWORD` and `CONNECTION_URL` in `Constants.java`. `Save` this file.

In the Application Navigator panel, double click on `tlMap1` under `DLCS Staff > Model > Application Sources > TopLink`. Under the `Database Info` tab, click `New...`. In the dialog box that pops up, choose a name for the connection (`DLCS-DEV`), use `Oracle (JDBC)` for `Connection Type`, `Next`, `dlcs` for Username, the same value as `DB_PASSWORD` in `Constants.java` for Password, click `Next`, the same value as `CONNECTION_URL` in `Constants.java` for `Enter Custom JDBC URL`, click `Next`, click `Test Connection`, `Next`, then `Finish`. Select that new connection in the dropdown menu for `Deployment Connection`, and save all files (`File > Save All`).

#### 4. Remove incorrect dependencies

In the Application Navigator panel, right click on `Model` under `DLCS Staff` and click `Project Properties`. In the left panel go to `Libraries`. Select `Struts Runtime` and click `Remove`.

Do the same for the `ViewController` project.

#### 5. Add dependencies

##### :point_right: Model

In the Application Navigator panel, right click on `Model` under `DLCS Staff` and click `Project Properties`.

In the left panel go to `Libraries`. Click `Add Jar/Directory` and navigate to `DLCS-Staff/JDeveloperLibs/model.lib`.

All of the following libraries (in *italics*) of `.jar` files should be present in the end; you will likely just need to manually add the files in libraries that are **_bold and italic_**. The others should already be there.

Also, be sure that the files in `Struts Runtime` come from `/opt/jdeveloper/jakarta-struts/lib`, and that `ordim.jar` and `ordhttp.jar` in `Oracle Intermedia` come from `/opt/jdeveloper/ord/jlib`. All others will come from `DLCS-Staff/JDeveloperLibs/model.lib`. The files with 'xxxxx' after their name will likely already be included, so no need to add again.

*TopLink*
+ `toplink.jar`
+ `toplink-oc4j.jar`
+ `antlr.jar`

*Commons Logging 1.0.3*

+ `commons-logging-api.jar`
+ `commons-logging.jar`

*Oracle Intermedia*

+ `bc4jimdomains.jar`
+ `ordim.jar`
+ `ordhttp.jar`

*SQLJ Runtime*

+ `runtime12.jar`

*J2EE*

+ `activation.jar`
+ `ejb.jar`
+ `jms.jar`
+ `jta.jar`
+ `mail.jar`
+ `servlet.jar`

**_JUnit Runtime_**

+ `junit.jar`

**_Struts Runtime_**

+ `antlr.jar`
+ `commons-beanutils.jar`
+ `commons-fileupload.jar`
+ `commons-digester.jar`
+ `commons-logging.jar`
+ `commons-validator.jar`
+ `jakarta-oro.jar`
+ `struts.jar`

*Oracle JDBC*

+ `ojdbc14dms.jar`
+ `orai18n.jar`
+ `ocrs12.jar`
+ `ojdl.jar`
+ `dms.jar`

*Oracle XML Parser v2*

+ `xmlparserv2.jar`
+ `xml.jar`

**_JDOM 1.0_**

+ `jdom.jar`

**_metadata_**

+ `mets-1_3_9.jar`
+ `mets.jar`
+ `mix.jar`
+ `mods3_4.jar`
+ `copyrightMD.jar`

**_solrj3.5_**

+ `apache-solr-solrj-3.5.0.jar`
+ `commons-codec-1.5.jar`
+ `commons-httpclient-3.1.jar`
+ `commons-io-1.4.jar`
+ `geronimo-stax-api_1.0_spec-1.0.1.jar`
+ `jcl-over-slf4j-1.6.1.jar`
+ `slf4j-api-1.6.1.jar`
+ `slf4j-jdk14-1.6.1.jar`
+ `wstx-asl-3.2.7.jar`
+ `jetty-util-6.1H.22.jar`
+ `jetty-util-7.0.0.pre5.jar`
+ `rome-1.0.jar`
+ `fontbox-1.6.0.jar`
+ `pdfbox-1.6.0.jar`
+ `poi-3.8-beta4.jar` xxxxx
+ `poi-ooxml-3.8-beta4.jar` xxxxx
+ `poi-ooxml-schemas-3.8-beta4.jar` xxxxx
+ `poi-scratchpad-3.8-beta4.jar` xxxxx
+ `tika-core-0.10.jar`
+ `tika-parsers-0.10.jar`

**_Sun JAXB RI_**

+ `jaxb-api.jar`
+ `jaxb-impl.jar`
+ `jsr173_1.0_api.jar`
    
**_Jenkov Prizetags_**

+ `jenkov-prizetags-bin-3.4.0.jar`

*standalone .jar*

+ `commons-lang-2.6.jar`
+ `slf4j-api-1.6.2.jar`
+ `poi-3.7-20101029.jar`
+ `commons-io-2.4.jar`
+ `commons-net-3.3.jar`
+ `commons-fileupload-1.3.jar`
+ `joda-time-2.3.jar`
+ `commons-vfs2-2.0.jar`
+ `xmlbeans-2.3.0.jar`
+ `dom4j-1.6.1.jar`
+ `cdlframe.jar`
+ `jargon.jar`

*standalone .zip*

+ `JimiProClasses.zip`

##### :point_right: ViewController

In the Application Navigator panel, right click on `ViewController` under `DLCS Staff` and click `Project Properties`.

In the left panel go to `Libraries`. Click `Add Jar/Directory` and navigate to `DLCS Staff/JDeveloperLibs/viewcontroller.lib`.

All of the following libraries (in *italics*) of `.jar` files should be present in the end; you will likely just need to manually add the files in libraries that are **_bold and italic_**. The others should already be there.

Also, be sure that the files in `Struts Runtime` come from `/opt/jdeveloper/jakarta-struts/lib`. All others will come from `DLCS-Staff/JDeveloperLibs/viewcontroller.lib`.

**_Struts Runtime_**

+ `antlr.jar`
+ `commons-beanutils.jar`
+ `commons-fileupload.jar`
+ `commons-digester.jar`
+ `commons-logging.jar`
+ `commons-validator.jar`
+ `jakarta-oro.jar`
+ `struts.jar`

*JSP Runtime*

+ `ojsp.jar`
+ `ojsputil.jar`
+ `oc4j.jar`
+ `oc4j-internal.jar`
+ `servlet.jar`
+ `ojc.jar`

*JSTL 1.1*

+ `jstl.jar`

*JSTL 1.1 Tags*

+ `standard.jar`

**_CPOI 3.0.1_**

+ `poi-3.0.1-FINAL-20070705.jar`
+ `poi-contrib-3.0.1-FINAL-20070705.jar`
+ `poi-scratchpad-3.0.1-FINAL-20070705.jar`

**_pg 1.0 Tag Libraries_**

+ `pager-taglib.jar`

**_string 1.1.0 Tag Libraries_**

+ `taglibs-string.jar`

**_unstandard 1.0 Tag Libraries_**

+ `taglibs-unstandard.jar`
    
*standalone .jar*

+ `slf4j-api-1.6.1.jar`
+ `slf4j-jdk14-1.6.1.jar`

# Build

In the Application Navigator panel, right click on the `Model` project and click `Rebuild`. Do the same for the `ViewController` project.

On the production it is deployed as .ear build artifact on jboss server with Apache serving html, css and javascript files

An Ant build script in the repo can be used to build and deploy on jboss or tomcat.

# Run

In the Application Navigator panel, find `index.jsp` under `DLCS Staff > Model > Web Content`. Right click it and click `Run`. A browser window will open with the login form.

To run this code change following file `DLCS-Staff\Model\src\commons-logging.properties` to use `simplelog` instead of `log4j`

# Rebuild

In the Application Navigator panel, click on the `Model` project. In the main menu, click `Run > Clean Model.jpr`. Do the same for the `ViewController` project (`Run > Clean ViewController.jpr`), then follow the build instructions above.

### Windows 10 Environment Setup
