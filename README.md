# J2V8AccessorHost


Perform the following steps to setup a J2V8 Android Accessor Host Application.
Step 1: Install Git for Windows
It can be downloaded for free from https://git-scm.com/downloads.Most settings available during the installation process should be compatible with Android studio. Just choose the settings you deem the most appropriate.
Step 2: Link Git executable to Android Studio
Open Android Studio and go to Settings. In the Setting dialog open the page Version Control / Git. Here defines the path to the Git executable you have just installed.
 


Step 3: Get the Path to your Repository from GitHub
HTTPS Git Repository link for the project:  https://git.txstate.edu/vkm4/J2V8AccessorHost.git
Use the above Link as a Git repository Link for the upcoming steps.
Step 4: Import the Git project to Android Studio
Go to Android Studio and go to Menu / VCS / Checkout from Version Control / Git
 
Paste the HTTPS path you obtained from GitHub in the previous step. The Parent Directory is the location where the Project will be placed in your local system. You can provide any location as per your convenience. In our case we have placed in ‘C:/J2V8’. This directory location will act as a workspace for your project. 
Select an appropriate Directory Name. We have provided ‘J2V8AccessotHost’ as a directory name. After filling all the required fields click [Clone].
 
The next step will show a popup window which asks for your GitHub username and password. Enter the credential and click [OK]
 
Now the project should be imported to Android Studio and you should be able to commit and push future changes back to GitHub if you’re provided with Access.
The Project comes with three default Accessors. They are as follows:
1-	Data Collection Accessor
2-	Fall Detection Accessor
3-	Alert Accessor
4-	TestAccessor
5-	TestCompositeAccessor
Test J2V8AccessorHost 
To test weather J2V8AccessorHost is working properly or not, Import the project in your Android Studio. Once done in the MainActivity.java file which is under the package com.txstate.j2v8.J2V8AccessorHost find the function “ReadRawJS” and connect TestAccessor as follows.
		      input = assetManager.open("lib/TestAccessor.js");

 Now run the Application in an emulator or Phone. If the J2V8AccessorHost is imported properly with desired android version, then the TestAccessor will be executed successfully and the output will be display in the phone/emulator screen.
The minimum requirement to run these Accessors are:
1-	Android Development IDE (Android Studio Recommended with version 6.0.0 and above) 
2-	Microsoft MS Band Smartwatch version 1.3.20307.2.
3-	Android Smartphone. (Android Version 6 and above)
