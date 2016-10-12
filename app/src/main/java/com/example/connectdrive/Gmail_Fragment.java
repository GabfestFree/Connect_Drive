package com.example.connectdrive;

import android.accounts.AccountManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ListView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.client.googleapis.extensions.android.gms.auth.GooglePlayServicesAvailabilityIOException;
import com.google.api.client.googleapis.extensions.android.gms.auth.UserRecoverableAuthIOException;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.ExponentialBackOff;
import com.google.api.services.gmail.GmailScopes;
import com.google.api.services.gmail.model.ListMessagesResponse;
import com.google.api.services.gmail.model.Message;
import com.google.api.services.gmail.model.MessagePart;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Gmail_Fragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Gmail_Fragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Gmail_Fragment extends Fragment  implements EasyPermissions.PermissionCallbacks{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
 static ListView filelist;
   static ArrayAdapter adapter;
    GoogleAccountCredential mCredential;
    int attachmentname;

   static ArrayList<String> files;
    HashMap<String, List<String>> Contents_category;
    static List<String> Content_list;
    static ExpandableListView Exp_list;
    static ExpandableListAdapter exadapter;
    ProgressDialog mProgress;
    static  List<String> attachlist;
    static  SharedPreferences prefs;

    static final int REQUEST_ACCOUNT_PICKER = 1000;
    static final int REQUEST_AUTHORIZATION = 1001;
    static final int REQUEST_GOOGLE_PLAY_SERVICES = 1002;
    static final int REQUEST_PERMISSION_GET_ACCOUNTS = 1003;
    static final int RESULT_OK= -1;

    private static final String BUTTON_TEXT = "Call Gmail API";
    private static final String PREF_ACCOUNT_NAME = "accountName";
    private static final String[] SCOPES = { GmailScopes.MAIL_GOOGLE_COM };
    View view;
    static SQLiteDatabase connectDatabase;
    static int brefresh;
    static int afterfresh=1;
    int attachid;
    int messgaeid;


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    private OnFragmentInteractionListener mListener;

    public Gmail_Fragment() {
        // Required empty public constructor
    }



    public static Gmail_Fragment newInstance(String param1, String param2) {
        Gmail_Fragment fragment = new Gmail_Fragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);

        }


    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
      view=inflater.inflate(R.layout.fragment_gmail_, container, false);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                getContext());

try {
    connectDatabase = getActivity().openOrCreateDatabase("connectDB", Context.MODE_PRIVATE, null);
    connectDatabase.execSQL("CREATE TABLE IF NOT EXISTS attachment (messageID VARCHAR,attachmentID VARCHAR,attachmentName VARCHAR)");
}
catch (Exception e)
{
   e.printStackTrace();

}
        Gmail_Fragment.prefs=PreferenceManager.getDefaultSharedPreferences(getActivity());
        brefresh=Gmail_Fragment.prefs.getInt("RefreshID",0);

        if( brefresh == 0) {
            alertDialogBuilder.setTitle("Sync with Your Gmail");

            // set dialog message
            alertDialogBuilder
                    .setMessage("Do you want Access your Gmail")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            prefs = PreferenceManager.getDefaultSharedPreferences(getActivity());
                            prefs.edit().putInt("RefreshID",afterfresh).commit();
                            try {
                               //delete all rows in a table
                                connectDatabase.delete("attachment",null,null);
                            }
                            catch(Exception e)
                            {

                            }
                            getResultsFromApi();

                        }
                    })
                    .setNegativeButton("No", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                            dialog.cancel();
                        }
                    });

            // create alert dialog
            AlertDialog alertDialog = alertDialogBuilder.create();

            // show it
            alertDialog.show();
        }


            Exp_list = (ExpandableListView) view.findViewById(R.id.exp_list);
            Contents_category = DataProvider.getInfo();
            Content_list = new ArrayList<String>(Contents_category.keySet());
            exadapter = new ContentAdapter(getActivity(), Contents_category, Content_list);
            Exp_list.setAdapter(exadapter);
            Exp_list.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
                @Override
                public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                    String selected = (String) exadapter.getChild(
                            groupPosition, childPosition);
                   try {
                       Cursor  attid= Gmail_Fragment.connectDatabase.rawQuery("SELECT DISTINCT messageID,attachmentID FROM attachment WHERE attachmentName = '"+selected+"'", null);
                       attachid = attid.getColumnIndex("attachmentID");
                       messgaeid=attid.getColumnIndex("messageID");

                       attid.moveToFirst();
                       while (attid != null) {
                           Log.i("Attachment Id",attid.getString(attachid));
                           Log.i("Message ID",attid.getString(messgaeid));
                         //  Toast.makeText(getActivity(),"Your id"+attid.getString(attachid),Toast.LENGTH_SHORT);
                          attid.moveToNext();
                       }


                   }
                   catch(Exception e)
                   {

                   }
                    return true;
                }
            });
            mCredential = GoogleAccountCredential.usingOAuth2(
                    getContext(), Arrays.asList(SCOPES))
                    .setBackOff(new ExponentialBackOff());

        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {

    }

    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {

    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
    private void getResultsFromApi() {
        if (! isGooglePlayServicesAvailable()) {
            acquireGooglePlayServices();
        } else if (mCredential.getSelectedAccountName() == null) {
            chooseAccount();
        } else if (! isDeviceOnline()) {
           // mOutputText.setText("No network connection available.");
        } else {
            new MakeRequestTask(mCredential).execute();
        }
    }
    @AfterPermissionGranted(REQUEST_PERMISSION_GET_ACCOUNTS)
    private void chooseAccount() {
        if (EasyPermissions.hasPermissions(
               getContext(), android.Manifest.permission.GET_ACCOUNTS)) {
            String accountName = getActivity().getPreferences(Context.MODE_PRIVATE)
                    .getString(PREF_ACCOUNT_NAME, null);
            if (accountName != null) {
                mCredential.setSelectedAccountName(accountName);
                getResultsFromApi();
            } else {
                // Start a dialog from which the user can choose an account
                startActivityForResult(
                        mCredential.newChooseAccountIntent(),
                        REQUEST_ACCOUNT_PICKER);
            }
        } else {
            // Request the GET_ACCOUNTS permission via a user dialog
            EasyPermissions.requestPermissions(
                    this,
                    "This app needs to access your Google account (via Contacts).",
                    REQUEST_PERMISSION_GET_ACCOUNTS,
                    android.Manifest.permission.GET_ACCOUNTS);
        }
    }
    @Override
    public void onActivityResult(
            int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch(requestCode) {
            case REQUEST_GOOGLE_PLAY_SERVICES:
                if (resultCode != RESULT_OK) {
                   // mOutputText.setText(
                          //  "This app requires Google Play Services. Please install " +
                               //     "Google Play Services on your device and relaunch this app.");
                } else {
                    getResultsFromApi();
                }
                break;
            case REQUEST_ACCOUNT_PICKER:
                if (resultCode == RESULT_OK && data != null &&
                        data.getExtras() != null) {
                    String accountName =
                            data.getStringExtra(AccountManager.KEY_ACCOUNT_NAME);
                    if (accountName != null) {
                        SharedPreferences settings =
                                getActivity().getPreferences(Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = settings.edit();
                        editor.putString(PREF_ACCOUNT_NAME, accountName);
                        editor.apply();
                        mCredential.setSelectedAccountName(accountName);
                        getResultsFromApi();
                    }
                }
                break;
            case REQUEST_AUTHORIZATION:
                if (resultCode == RESULT_OK) {
                    getResultsFromApi();
                }
                break;
        }
    }
    @Override
   public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(
                requestCode, permissions, grantResults, this);
    }
    private boolean isDeviceOnline() {
        ConnectivityManager connMgr =
                (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }
    private boolean isGooglePlayServicesAvailable() {
        GoogleApiAvailability apiAvailability =
                GoogleApiAvailability.getInstance();
        final int connectionStatusCode =
                apiAvailability.isGooglePlayServicesAvailable(getActivity());
        return connectionStatusCode == ConnectionResult.SUCCESS;
    }
    private void acquireGooglePlayServices() {
        GoogleApiAvailability apiAvailability =
                GoogleApiAvailability.getInstance();
        final int connectionStatusCode =
                apiAvailability.isGooglePlayServicesAvailable(getActivity());
        if (apiAvailability.isUserResolvableError(connectionStatusCode)) {
            showGooglePlayServicesAvailabilityErrorDialog(connectionStatusCode);
        }
    }
    void showGooglePlayServicesAvailabilityErrorDialog(
            final int connectionStatusCode) {
        GoogleApiAvailability apiAvailability = GoogleApiAvailability.getInstance();
        Dialog dialog = apiAvailability.getErrorDialog(
                getActivity(),
                connectionStatusCode,
                REQUEST_GOOGLE_PLAY_SERVICES);
        dialog.show();

    }
    private class MakeRequestTask extends AsyncTask<Void, Void, List<String>> {
        private com.google.api.services.gmail.Gmail mService = null;
        private Exception mLastError = null;

        public MakeRequestTask(GoogleAccountCredential credential) {
            HttpTransport transport = AndroidHttp.newCompatibleTransport();
            JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();
            mService = new com.google.api.services.gmail.Gmail.Builder(
                    transport, jsonFactory, credential)
                    .setApplicationName("Gmail API Android Quickstart")
                    .build();
        }

        /**
         * Background task to call Gmail API.
         * @param params no parameters needed for this task.
         */
        @Override
        protected List<String> doInBackground(Void... params) {
            try {
                return getDataFromApi();
            } catch (Exception e) {
                mLastError = e;
                cancel(true);
                return null;
            }
        }

        /**
         * Fetch a list of Gmail labels attached to the specified account.
         * @return List of Strings labels.
         * @throws IOException
         */
        private List<String> getDataFromApi() throws IOException {
            // Get the labels in the user's account.
            String user = "me";
            List<String> labels = new ArrayList<String>();
           attachlist=new ArrayList<String>();
            ListMessagesResponse listResponse = mService.users().messages().list(user).execute();
//                    mService.users().labels().list(user).execute();
            for (Message message : listResponse.getMessages()) {

                Message msg = mService.users().messages().get(user, message.getId()).execute();
              //  labels.add("Msg ID: " + message.getId());
                com.google.api.services.gmail.model.MessagePart messagePart = msg.getPayload();

                if(messagePart.getParts() != null) {
                    List<MessagePart> parts = messagePart.getParts();

                    for (MessagePart part : parts) {
                        if (part.getFilename() != null && part.getFilename().length() > 0) {

                            labels.add("Attach id: " + part.getBody().getAttachmentId() + "  File Name: " + part.getFilename());
                            attachlist.add(part.getFilename());
                         connectDatabase.execSQL("INSERT INTO attachment (messageID,attachmentID,attachmentName) VALUES('"+message.getId()+"','"+part.getBody().getAttachmentId()+"','"+part.getFilename()+"')");
                        }
                    }
                }
//                + "  " + mService.users().messages().get(user,message.getId()).execute().getSnippet());
            }
            return labels;
        }


        @Override
        protected void onPreExecute() {

        }

        @Override
        protected void onPostExecute(List<String> output) {

            if (output == null || output.size() == 0) {
               // mOutputText.setText("No results returned.");
                Log.i("No results","Returned");
            } else {

              // Log.i("Attchments",output.toString());

                try {
                    Cursor c = Gmail_Fragment.connectDatabase.rawQuery("SELECT DISTINCT attachmentName FROM attachment", null);
                    attachmentname = c.getColumnIndex("attachmentName");

                    c.moveToFirst();
                    while (c != null) {
                        Log.i("Attachment Name", c.getString(attachmentname));
                        c.moveToNext();
                    }

                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }


                //  output.add(0, "Data retrieved using the Gmail API:");
               // mOutputText.setText(TextUtils.join("\n", output));
            }
        }

        @Override
        protected void onCancelled() {
            if (mLastError != null) {
                if (mLastError instanceof GooglePlayServicesAvailabilityIOException) {
                    showGooglePlayServicesAvailabilityErrorDialog(
                            ((GooglePlayServicesAvailabilityIOException) mLastError)
                                    .getConnectionStatusCode());
                } else if (mLastError instanceof UserRecoverableAuthIOException) {
                    startActivityForResult(
                            ((UserRecoverableAuthIOException) mLastError).getIntent(),
                            Gmail_Fragment.REQUEST_AUTHORIZATION);
                } else {
                   // mOutputText.setText("The following error occurred:\n"
                         //   + mLastError.getMessage());
                    Log.i("The following error occured",mLastError.getMessage());
                }
            } else {
               // mOutputText.setText("Request cancelled.");
                Log.i("request","Cancelled");
            }
        }
    }
}
