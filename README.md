# MultiProcessPreferences

Simple to use Library that allows the use of Shared Preferences across multiple processes.  Uses a Content Provider to
wrap Shared Preferences and is safe to use across multiple processes.

# Usage

Clone the project and use the "multi preferences" module

Setup your Manifest in the module, either leave as it is or update :

        <provider
            android:authorities="<your_package_name>.multi_preferences.MultiProvider"
            android:name=".multi_preferences.MultiProvider"
            android:process=":multi_preferences"
            android:exported="false"/>
            
  
  Next in MultiProvider.class do the same:


          private static final String PROVIDER_NAME =
            "<your_package_name>.multi_preferences.MultiProvider";

You'll now be able to start using the library like this:
  
    MultiPreferences preferences = new MultiPreferences("Preferences_Name", android.content.ContentResolver);
