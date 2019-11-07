package com.otto.sdk.ui.activity.kycupgrade;

import java.io.File;

public class FileUtil {

//    public static void parseJSONFile(String nameFile, Context context) {
//
//        try {
//            String jsonContent = getAssetJsonData(nameFile, context);
//
//            Json json = Json.parse(jsonContent);
//            Map jsonMap = json.asObject();
//            List<Json> data = json.asObject().get("provinces").asArray();
//            Json d0 = data.get(0).asObject().get("description");
//            Json c0 = data.get(0).asObject().get("cities").asArray().get(0);
//
//
////            JSONArray array = data
////            String str = ((JSONObject) array.get(0)).getString("description");
//            Toast.makeText(context, "value= "+d0.toString()+"/ "+c0.asObject().get("description").toString(), Toast.LENGTH_SHORT).show();
//            Log.d("", "----");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    public static String getAssetJsonData(String nameFile, Context context) {
//        String json = null;
//        try {
//            InputStream is = context.getAssets().open(nameFile);
//            int size = is.available();
//            byte[] buffer = new byte[size];
//            is.read(buffer);
//            is.close();
//            json = new String(buffer, "UTF-8");
//        } catch (IOException ex) {
//            ex.printStackTrace();
//            return null;
//        }
//
//        Log.e("data", json);
//        return json;
//
//    }

    public static int removeDirectory(final File folder) {

        if (folder.isDirectory() == true) {
            File[] folderContents = folder.listFiles();
            int deletedFiles = 0;

            if (folderContents.length == 0) {
                if (folder.delete()) {
                    deletedFiles++;
                    return deletedFiles;
                }
            } else if (folderContents.length > 0) {

                do {

                    File lastFolder = folder;
                    File[] lastFolderContents = lastFolder.listFiles();

                    //This while loop finds the deepest path that does not contain any other folders
                    do {

                        for (File file : lastFolderContents) {

                            if (file.isDirectory()) {
                                lastFolder = file;
                                lastFolderContents = file.listFiles();
                                break;
                            } else {

                                if (file.delete()) {
                                    deletedFiles++;
                                } else {
                                    break;
                                }

                            }//End if(file.isDirectory())

                        }//End for(File file : folderContents)

                    } while (lastFolder.delete() == false);

                    deletedFiles++;
                    if (folder.exists() == false) {
                        return deletedFiles;
                    }

                } while (folder.exists());
            }
        } else {
            return -1;
        }

        return 0;
    }

}
