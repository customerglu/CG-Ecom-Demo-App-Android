package com.customerglu.sdk.pip;

import static com.customerglu.sdk.Utils.CGConstants.PIP_DATE;
import static com.customerglu.sdk.Utils.Comman.printErrorLogs;

import android.net.Uri;
import android.util.Log;

import com.customerglu.sdk.CustomerGlu;
import com.customerglu.sdk.Interface.PIPVideoDownloadedListener;
import com.customerglu.sdk.Modal.EntryPointsModel;
import com.customerglu.sdk.Modal.MobileData;
import com.customerglu.sdk.Utils.Comman;
import com.customerglu.sdk.Utils.Prefs;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PIPHelper {

    public static PIPHelper pipHelper;
    public boolean isLoaded = false;
    public MobileData pipEntryPointsData;
    private boolean isDraggable = false;
    public boolean muteOnDefaultPIP = false;
    public boolean muteOnDefaultExpanded = false;
    public boolean loopVideoPIP = false;
    public boolean loopVideoExpanded = false;
    public boolean darkPlayer = false;
    public boolean removeOnDismissExpanded = false;
    public boolean removeOnDismissPIP = false;
    public int verticalPadding = 0;
    public int horizontalPadding = 0;
    public String viewPosition = "BOTTOM-LEFT";
    public List<String> allowedActivityList;
    public List<String> disAllowedActivityList;
    Uri videoUri;
    public Boolean dailyRefresh = false;
    String pipEntryPointId = "";
    String pipEntryPointName = "";
    boolean isDismissed = false;
    public String videoUrl = "";
    public String videoFileName = "";
    private PIPVideoDownloadedListener pipVideoDownloadedListener;
    String videoPath = "";
    int delay = 0;

    public enum DISPLAY_UNIT_TYPE {
        DP,
        PX
    }

    public static PIPHelper getInstance() {
        if (pipHelper == null) {
            pipHelper = new PIPHelper();
        }
        return pipHelper;
    }

    public void setPIPDownloadListener(PIPVideoDownloadedListener pipVideoDownloadedListener) {
        this.pipVideoDownloadedListener = pipVideoDownloadedListener;
    }


    public void initPIP() {
        pipEntryPointsData = getPipEntryPointData();
        setUpPipViewConditions();
        setUpPipViewContainer();
        downloadPipVideo();
    }

    public void isPIPDismissed() {
        isDismissed = true;
    }

    public void addMarginsForPIP(int horizontal, int vertical, DISPLAY_UNIT_TYPE unit_type) {
        switch (unit_type) {
            case PX:
                horizontalPadding = horizontal;
                verticalPadding = vertical;
                break;
            case DP:
                horizontalPadding = (int) Comman.convertDpToPixel(horizontal, CustomerGlu.globalContext);
                verticalPadding = (int) Comman.convertDpToPixel(vertical, CustomerGlu.globalContext);
                break;
        }

    }

    public boolean isDraggable() {
        return isDraggable;
    }

    public MobileData.ActionData getCTAAction() {
        if (pipEntryPointsData != null && pipEntryPointsData.getContent().get(0).getAction() != null) {
            return pipEntryPointsData.getContent().get(0).getAction();
        }
        return null;
    }

    private MobileData getPipEntryPointData() {

        EntryPointsModel entryPointsModel;
        entryPointsModel = CustomerGlu.entryPointsModel;

        if (entryPointsModel != null && entryPointsModel.getSuccess()) {
            isLoaded = true;

            if (entryPointsModel.getEntryPointsData() != null && entryPointsModel.getEntryPointsData().size() > 0) {

                for (int i = 0; i < entryPointsModel.getEntryPointsData().size(); i++) {

                    if (entryPointsModel.getEntryPointsData().get(i).getMobileData() != null) {

                        if (entryPointsModel.getEntryPointsData().get(i).getMobileData().getContainer() != null && entryPointsModel.getEntryPointsData().get(i).getMobileData().getConditions() != null && entryPointsModel.getEntryPointsData().get(i).getMobileData().getContent() != null && entryPointsModel.getEntryPointsData().get(i).getMobileData().getContent().size() > 0) {


                            if (entryPointsModel.getEntryPointsData().get(i).getVisible()) {

                                if (entryPointsModel.getEntryPointsData().get(i).getMobileData().getContainer().getType().equalsIgnoreCase("PIP")) {
                                    if (entryPointsModel.getEntryPointsData().get(i).get_id() != null) {
                                        pipEntryPointId = entryPointsModel.getEntryPointsData().get(i).get_id();
                                    }
                                    if (entryPointsModel.getEntryPointsData().get(i).getName() != null) {
                                        pipEntryPointName = entryPointsModel.getEntryPointsData().get(i).getName();
                                    }

                                    pipEntryPointsData = entryPointsModel.getEntryPointsData().get(i).getMobileData();
                                    break;
                                }
                            }
                        }
                    }
                }
            }
        }

        return pipEntryPointsData;
    }

    private void setUpPipViewConditions() {
        if (pipEntryPointsData != null) {
            if (pipEntryPointsData.getConditions().getDraggable() != null) {
                isDraggable = pipEntryPointsData.getConditions().getDraggable();
            }
            if (pipEntryPointsData.getConditions().getShowCount() != null && pipEntryPointsData.getConditions().getShowCount().isDailyRefresh() != null && pipEntryPointsData.getConditions().getShowCount().isDailyRefresh()) {
                dailyRefresh = pipEntryPointsData.getConditions().getShowCount().isDailyRefresh();
            }
            if (pipEntryPointsData.getConditions().getPip() != null) {
                if (pipEntryPointsData.getConditions().getPip().getDarkPlayer() != null) {
                    darkPlayer = pipEntryPointsData.getConditions().getPip().getDarkPlayer();
                }
                if (pipEntryPointsData.getConditions().getPip().getLoopVideoPIP() != null) {
                    loopVideoPIP = pipEntryPointsData.getConditions().getPip().getLoopVideoPIP();
                }
                if (pipEntryPointsData.getConditions().getPip().getLoopVideoExpanded() != null) {
                    loopVideoExpanded = pipEntryPointsData.getConditions().getPip().getLoopVideoExpanded();
                }
                if (pipEntryPointsData.getConditions().getPip().getMuteOnDefaultPIP() != null) {
                    muteOnDefaultPIP = pipEntryPointsData.getConditions().getPip().getMuteOnDefaultPIP();
                }
                if (pipEntryPointsData.getConditions().getPip().getMuteOnDefaultExpanded() != null) {
                    muteOnDefaultExpanded = pipEntryPointsData.getConditions().getPip().getMuteOnDefaultExpanded();
                }
                if (pipEntryPointsData.getConditions().getPip().getRemoveOnDismissPIP() != null) {
                    removeOnDismissPIP = pipEntryPointsData.getConditions().getPip().getRemoveOnDismissPIP();
                }
                if (pipEntryPointsData.getConditions().getPip().getRemoveOnDismissExpanded() != null) {
                    removeOnDismissExpanded = pipEntryPointsData.getConditions().getPip().getRemoveOnDismissExpanded();
                }
                if (pipEntryPointsData.getConditions().getDelay() != null && delay == 0) {
                    delay = pipEntryPointsData.getConditions().getDelay();
                }


            }
        }
    }


    private void setUpPipViewContainer() {
        if (pipEntryPointsData != null) {
            if (pipEntryPointsData.getContainer().getHorizontal_padding() != null && horizontalPadding == 0) {
                horizontalPadding = Integer.parseInt(pipEntryPointsData.getContainer().getHorizontal_padding());
            }
            if (pipEntryPointsData.getContainer().getVertical_padding() != null && verticalPadding == 0) {
                verticalPadding = Integer.parseInt(pipEntryPointsData.getContainer().getVertical_padding());

            }
            if (pipEntryPointsData.getContainer().getPosition() != null) {
                viewPosition = pipEntryPointsData.getContainer().getPosition();
            }

            if (pipEntryPointsData.getContainer().getAndroid() != null) {
                allowedActivityList = pipEntryPointsData.getContainer().getAndroid().getAllowedActitivityList();
                disAllowedActivityList = pipEntryPointsData.getContainer().getAndroid().getDisallowedActitivityList();
            }

        }
    }

    public String getVideoPath() {
        return videoPath;
    }

    public List<String> getAllowedActivityListList() {
        return allowedActivityList;
    }

    public List<String> getDisAllowedActivityListList() {
        return disAllowedActivityList;
    }

    public String getPipVideoUrl() {
        if (pipEntryPointsData != null) {

            if (pipEntryPointsData.getContent().get(0) != null) {
                if (pipEntryPointsData.getContent().get(0).getUrl() != null) {
                    return pipEntryPointsData.getContent().get(0).getUrl();
                }
            }
        }
        return "";
    }

    public boolean checkShowOnDailyRefresh() {
        if (isDismissed) {
            return false;
        }
        if (dailyRefresh) {
            String daily_refresh_date = Prefs.getEncKey(CustomerGlu.globalContext, PIP_DATE);
            String date = new SimpleDateFormat("dd", Locale.getDefault()).format(new Date());
            if (!daily_refresh_date.equalsIgnoreCase("")) {
                if (!daily_refresh_date.equalsIgnoreCase(date)) {
                    Prefs.putEncKey(CustomerGlu.globalContext, PIP_DATE, date);
                } else {
                    return false;
                }
            } else {
                Prefs.putEncKey(CustomerGlu.globalContext, PIP_DATE, date);
            }
        }
        return true;

    }

    public int getPIPDelay() {
        return delay;
    }

    public void setDelay(int pipDelay) {
        delay = pipDelay;
    }

    private String getVideoUrl() {
        if (pipEntryPointsData != null) {
            if (pipEntryPointsData.getContent().get(0) != null && pipEntryPointsData.getContent().get(0).getUrl() != null && !pipEntryPointsData.getContent().get(0).getUrl().isEmpty()) {
                videoUrl = pipEntryPointsData.getContent().get(0).getUrl();
                saveFileName(videoUrl);
            }
        }
        return videoUrl;
    }

    private void saveFileName(String url) {
        String[] split = url.split("/");
        int size = split.length;
        videoFileName = split[size - 1];


    }

    public void sendPIPAnalyticsEvents(String eventName, String screenName, boolean isExpanded) {
        HashMap<String, Object> eventData = new HashMap<>();
        eventData.put("entry_point_id", pipEntryPointId);
        eventData.put("entry_point_name", pipEntryPointName);
        eventData.put("entry_point_location", screenName);
        eventData.put("entry_point_container", "PIP");
        eventData.put("entry_point_is_expanded", "" + isExpanded);
        CustomerGlu.getInstance().cgAnalyticsEventManager(CustomerGlu.globalContext, eventName, "", eventData);
    }

    private String getVideoFileName() {
        return videoFileName;
    }

    public Uri getPIPVideoUri() {

        return videoUri;

    }

    private void downloadPipVideo() {
        if (getVideoUrl() != null && getVideoFileName() != null && !getVideoFileName().isEmpty()) {

            if (isPiPCacheDirectoryExists() && isPiPVideoCached(getVideoFileName())) {
                retrievePIPVideoPath(getVideoFileName());
                pipVideoDownloadedListener.onVideoDownloaded();

                return;
            }


            try {
                Log.e("ds", "pip video start downloaded");

                Comman.getApiToken().downloadVideo(getVideoUrl()).enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        // Get the response body

                        // Save the video to internal storage
                        if (response.body() != null) {
                            ResponseBody body = response.body();
                            try {

                                saveVideoToInternalStorage(body);

                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Log.e("ds", "pip video failed");

                    }
                });

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void saveVideoToInternalStorage(ResponseBody body) throws IOException {

        try {
            //      saveVideoToInternalStorage(body);
            String videoFileName = getVideoFileName();

            // Create Cache directory for Video
            File cgFileDir = new File(CustomerGlu.globalContext.getFilesDir(), "CG-Cache");
            if (!cgFileDir.exists()) {
                cgFileDir.mkdir();
            }
            // Choose a filename
            File videoFile = new File(cgFileDir, videoFileName);

            InputStream inputStream = null;
            OutputStream outputStream = null;

            try {
                byte[] fileReader = new byte[4096];
                long fileSize = body.contentLength();
                long fileSizeDownloaded = 0;
                inputStream = body.byteStream();
                outputStream = new FileOutputStream(videoFile);

                while (true) {
                    int read = inputStream.read(fileReader);
                    if (read == -1) {
                        break;
                    }
                    outputStream.write(fileReader, 0, read);
                    fileSizeDownloaded += read;
                }

                outputStream.flush();

                // The video has been successfully downloaded and saved to internal storage
                videoPath = videoFile.getAbsolutePath();
                videoUri = Uri.fromFile(videoFile);
                pipVideoDownloadedListener.onVideoDownloaded();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (inputStream != null) {
                    inputStream.close();
                }

                if (outputStream != null) {
                    outputStream.close();
                }
            }
        } catch (Exception e) {
            printErrorLogs("" + e);
        }

    }

//    private void loadVideoFromInternalStorage(String filePath) {
//
//        videoUri = Uri.parse(Environment.getExternalStorageDirectory() + filePath);
//
//
//    }

    public boolean isPiPCacheDirectoryExists() {
        try {
            File file = new File(CustomerGlu.globalContext.getFilesDir(), "CG-Cache");
            return file.exists();
        } catch (Exception exception) {

        }

        return false;
    }

    public boolean isPiPVideoCached(String videoFileName) {
        try {
            File cgFileDir = new File(CustomerGlu.globalContext.getFilesDir(), "CG-Cache");
            File videoFileDirectory = new File(cgFileDir.getAbsolutePath() + "/" + videoFileName);

            return videoFileDirectory.exists();
        } catch (Exception exception) {

        }
        return false;
    }

    public void retrievePIPVideoPath(String videoFileName) {
        try {
            File cgFileDir = new File(CustomerGlu.globalContext.getFilesDir(), "CG-Cache");
            File videoFileDirectory = new File(cgFileDir.getAbsolutePath() + "/" + videoFileName);
            videoPath = videoFileDirectory.getAbsolutePath();
        } catch (Exception exception) {

        }
    }

}
