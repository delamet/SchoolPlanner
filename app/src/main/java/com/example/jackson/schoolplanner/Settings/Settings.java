package com.example.jackson.schoolplanner.Settings;

/**
 * Created by Jackson on 8/22/17.
 */

public class Settings {

    private boolean setup;

    public Settings(boolean setup){
        this.setup = setup;
    }

    public boolean hasSetup(){
        return setup;
    }
}
