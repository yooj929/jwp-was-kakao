package model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Extension {

    private final String extension;

    public boolean isStatic(){
        if(extension.equals("html") || extension.equals("ico")){
            return false;
        }
        return true;
    }
}
