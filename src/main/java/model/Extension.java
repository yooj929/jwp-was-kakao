package model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
public class Extension {

    private final String extension;

    public Extension(String extension) {
        this.extension = extension;
    }

    public boolean isStatic(){
        if(extension.equals("html") || extension.equals("ico")){
            return false;
        }
        return true;
    }
}
