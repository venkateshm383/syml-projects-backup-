package requireddoc.infrastructure;

import java.io.BufferedReader;
import java.io.FileReader;


import play.Play;
import com.fasterxml.jackson.databind.ObjectMapper;


public class ReadTextFile {


    public static TestApplicants readFromFile(String fileLocation) {
        try {
        	

            FileReader filereader = new FileReader(fileLocation);
            StringBuilder sb = new StringBuilder();
            BufferedReader br = null;

            try {
                br = new BufferedReader(filereader);
                String line = null;
                while ((line = br.readLine()) != null) {
                    sb.append(line);
                }
            } catch (Exception ex) {
                //
            } finally {
                if (br != null)
                    br.close();
            }
            filereader.close();
            			
            	String val=sb.substring(1);
            ObjectMapper mapper = new ObjectMapper();
            TestApplicants user = mapper.readValue(val.toString().trim(), TestApplicants.class);

            return user;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
