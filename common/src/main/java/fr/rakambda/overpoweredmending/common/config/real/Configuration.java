package fr.rakambda.overpoweredmending.common.config.real;

import com.google.gson.annotations.Expose;
import fr.rakambda.overpoweredmending.common.config.IConfiguration;
import fr.rakambda.overpoweredmending.common.config.enums.SelectionMode;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.jspecify.annotations.NonNull;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Data
@Log4j2
public class Configuration implements IConfiguration{
	@Expose
	@NonNull
	private SelectionMode selectionMode = SelectionMode.PERCENTAGE;
	
	public static Configuration read() throws RuntimeException{
		var path = getConfigPath();
		try{
			return ConfigLoader.loadConfig(new Configuration(), Configuration.class, path);
		}
		catch(IOException e){
			log.error("Failed to get OverpoweredMending configuration from {}, using default", path, e);
			return new Configuration();
		}
	}
	
	public void onUpdate(){
		var path = getConfigPath();
		try{
			ConfigLoader.saveConfig(this, path);
		}
		catch(IOException e){
			log.error("Failed to save OverpoweredMending configuration to {}", path, e);
		}
	}
	
	private static Path getConfigPath(){
		return Paths.get(".").resolve("config").resolve("overpoweredmending.json");
	}
}
