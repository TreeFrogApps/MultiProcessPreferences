# MultiProcessPreferences

Simple to use Library that allows the use of Shared Preferences across multiple processes.  Uses a Content Provider to
wrap Shared Preferences and is safe to use across multiple processes.

# Usage

Copy the library folder "multi_preferences" into your app package i.e. java.com.mycompany.myappname..multi_preferences

Setup your Manifest to include the provider :

        <provider
            android:authorities="<your_package_name>.multi_preferences.MultiProvider"
            android:name=".multi_preferences.MultiProvider"
            android:process=":multi_preferences"
            android:exported="false"
            android:multiprocess="true"/>
            
  You'll now be able to start using the library like this:
  
  MultiPreferences preferences = new MultiPreferences("Prefences_Name, getApplicationContext());
