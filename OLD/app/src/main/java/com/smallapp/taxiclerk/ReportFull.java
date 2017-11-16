/*
 * 
 * 
 *
 * Copyright 2014 © small-app.com
 * 
 * Author: Ivan Dinkov
 * 
 * 
 * 
 */
package com.smallapp.taxiclerk;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.smallapp.taxiclerk.sqlite.DatabaseHandler;
import com.smallapp.taxiclerk.sqlite.Expense;
import com.smallapp.taxiclerk.sqlite.Provider;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

// TODO: Auto-generated Javadoc

/**
 * The Class ReportFull.
 */
public class ReportFull extends Fragment {

    // All Static variables

    // Database Name

    /**
     * The Constant TABLE_EXPENSE.
     */
    private static final String TABLE_EXPENSE = "expense";

    /**
     * The Constant TABLE_INCOME.
     */
    private static final String TABLE_INCOME = "income";

    /**
     * The Constant TABLE_HOURS.
     */
    private static final String TABLE_HOURS = "hours";

    /**
     * The Constant KEY_EXPENSE_NAME.
     */
    private static final String KEY_EXPENSE_NAME = "expense_name";

    /**
     * The Constant KEY_AMOUNT.
     */
    private static final String KEY_AMOUNT = "amount";

    /**
     * The Constant KEY_DATE.
     */
    private static final String KEY_DATE = "date";

    /**
     * The Constant KEY_MILAGE.
     */
    private static final String KEY_MILAGE = "milage";

    /**
     * The Constant KEY_PAYMENT_TYPE.
     */
    private static final String KEY_PAYMENT_TYPE = "payment_type";

    /**
     * The Constant KEY_ACTIVE.
     */
    private static final String KEY_ACTIVE = "active";

    // Income Table Column names

    /**
     * The Constant KEY_INC_DATE.
     */
    private static final String KEY_INC_DATE = "income_date";

    /**
     * The Constant KEY_INC_TYPE.
     */
    private static final String KEY_INC_TYPE = "income_type";

    /**
     * The Constant KEY_INC_AMOUNT.
     */
    private static final String KEY_INC_AMOUNT = "income_amount";

    /**
     * The Constant KEY_INC_ACTIVE.
     */
    private static final String KEY_INC_ACTIVE = "income_active";

    /**
     * The Constant KEY_INC_PROVIDER.
     */
    private static final String KEY_INC_PROVIDER = "income_provider";

    // Hours table

    /**
     * The Constant KEY_HOURS.
     */
    private static final String KEY_HOURS = "hours";

    /**
     * The Constant KEY_HOURS_DATE.
     */
    private static final String KEY_HOURS_DATE = "hours_date";

    /**
     * The Constant KEY_HOURS_ACTIVE.
     */
    private static final String KEY_HOURS_ACTIVE = "hours_active";

    // Provider table

    /**
     * The txt email.
     */
    private EditText txtEmail;

    /**
     * The txt email base.
     */
    private TextView txtEmailBase;

    /**
     * The btn generate.
     */
    private Button btnGenerate;

    /**
     * The _email.
     */
    private String _email;

    /**
     * The answer.
     */
    private String answer = "sd_card";

    /**
     * The sdf.
     */
    private SimpleDateFormat sdf;

    /**
     * The today.
     */
    private String today;

    /**
     * The attachment_dir.
     */
    protected String attachment_dir;

    /**
     * The attachment.
     */
    protected File attachment;

    /**
     * The all jobs.
     */
    private String allJobs;

    /**
     * The cash provider2.
     */
    private String cashProvider2;

    /**
     * The cash provider3.
     */
    private String cashProvider3;

    /**
     * The cash provider1.
     */
    private String cashProvider1;

    /**
     * The income total.
     */
    private String incomeTotal = "0.00";

    /**
     * The expense total.
     */
    private String expenseTotal = "0.00";

    /**
     * The acc provider1.
     */
    private String accProvider1;

    /**
     * The acc provider2.
     */
    private String accProvider2;

    /**
     * The acc provider2.
     */
    private String accProvider3;

    /**
     * The pr2.
     */
    private String pr2;

    /**
     * The pr3.
     */
    private String pr3;

    /**
     * The euro.
     */
    protected String euro = "€ ";

    /**
     * The diesel cash.
     */
    private String dieselCash = "0.00";

    /**
     * The diesel acc.
     */
    private String dieselAcc = "0.00";

    /**
     * The diesel bank acc.
     */
    private String dieselBankAcc = "0.00";

    /**
     * The ins cash.
     */
    private String insCash = "0.00";

    /**
     * The ins acc.
     */
    private String insAcc = "0.00";

    /**
     * The ins bank acc.
     */
    private String insBankAcc = "0.00";

    /**
     * The base cash.
     */
    private String baseCash = "0.00";

    /**
     * The base acc.
     */
    private String baseAcc = "0.00";

    /**
     * The base bank acc.
     */
    private String baseBankAcc = "0.00";

    /**
     * The phone cash.
     */
    private String phoneCash = "0.00";

    /**
     * The phone acc.
     */
    private String phoneAcc = "0.00";

    /**
     * The phone bank acc.
     */
    private String phoneBankAcc = "0.00";

    /**
     * The misc cash.
     */
    private String miscCash = "0.00";

    /**
     * The misc acc.
     */
    private String miscAcc = "0.00";

    /**
     * The misc bank acc.
     */
    private String miscBankAcc = "0.00";

    /**
     * The all hours.
     */
    private String allHours = "0";

    /**
     * The maint cash.
     */
    private String maintCash = "0.00";

    /**
     * The maint acc.
     */
    private String maintAcc = "0.00";

    /**
     * The maint bank acc.
     */
    private String maintBankAcc = "0.00";

    /**
     * The uri.
     */
    private Uri URI;

    /**
     * The UR i_att.
     */
    private Uri URI_att;

    /**
     * The m connection.
     */
    private Boolean mConnection = false;

    private String minMileage;

    private String difMileage;

    private TextView txtStartDateFullReport, txtEndDateFullReport;
    private String startDateToSearch, endDateToSearch;
    private String startDateToDisplay, endDateToDisplay;


    /*
     * (non-Javadoc)
     *
     * @see
     * android.support.v4.app.Fragment#onCreateView(android.view.LayoutInflater,
     * android.view.ViewGroup, android.os.Bundle)
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater
                .inflate(R.layout.report_full, container, false);
        getActivity().getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        /*
      The rd save phone.
     */
        RadioButton rdSavePhone = (RadioButton) rootView.findViewById(R.id.rdSavePhone);
        /*
      The rd send email.
     */
        RadioButton rdSendEmail = (RadioButton) rootView.findViewById(R.id.rdSendEmail);
        txtEmail = (EditText) rootView.findViewById(R.id.txtReportEmail);
        txtEmailBase = (TextView) rootView.findViewById(R.id.txtBaseFullReport);
//        TextView txtStartDateFullReport = this.txtStartDateFullReport;
        txtStartDateFullReport = (TextView) rootView.findViewById(R.id.txtStartDateFullReport);
        txtEndDateFullReport = (TextView) rootView.findViewById(R.id.txtEndDateFullReport);
        btnGenerate = (Button) rootView
                .findViewById(R.id.btnGenerateFullReport);
        Button btnPickStartDateFullReport = (Button) rootView.findViewById(R.id.btnPickStartDateFullReport);
        Button btnPickEndDateFullReport = (Button) rootView.findViewById(R.id.btnPickEndDateFullReport);

        txtEmailBase.setEnabled(false);
        txtEmailBase.setClickable(false);
        txtEmailBase.setFocusable(false);

        txtEmail.setVisibility(View.GONE);
        txtEmailBase.setVisibility(View.GONE);

        today = setCurrentDate();
        mConnection = checkConnection();

        txtEmail.setHintTextColor(getResources().getColor(
                R.color.login_label_color));


        btnPickStartDateFullReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putInt("DATE", 1);

                DialogFragment newFragment = new DatePickerFragment();
                newFragment.setArguments(bundle);

                newFragment.show(getFragmentManager(), "datePicker");
            }
        });

        btnPickEndDateFullReport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putInt("DATE", 2);

                DialogFragment newFragment = new DatePickerFragment();
                newFragment.setArguments(bundle);

                newFragment.show(getFragmentManager(), "datePicker");

            }
        });
        // get radio
        rdSavePhone.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                txtEmail.setVisibility(View.GONE);
                txtEmailBase.setVisibility(View.GONE);
                txtEmailBase
                        .setBackgroundResource(R.drawable.edittext_login_box_base);
                txtEmail.setText("");
                answer = "sd_card";
                btnGenerate.setText("GENERATE");
            }
        });
        rdSendEmail.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                txtEmail.setVisibility(View.VISIBLE);
                txtEmailBase.setVisibility(View.VISIBLE);
                txtEmailBase
                        .setBackgroundResource(R.drawable.edittext_login_box_base);
                answer = "email";
                btnGenerate.setText("SEND");
            }
        });

        btnGenerate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                int z = 0;
                int x = 0;
                try {
                    x = Integer.valueOf(startDateToSearch.replaceAll("[^0-9]", ""));
                    z = Integer.valueOf(endDateToSearch.replaceAll("[^0-9]", ""));

                } catch (Exception e) {
                    // TODO: handle exception
                }
                if (startDateToSearch == null || endDateToSearch == null) {
                    Toast.makeText(getActivity(), "Please select Date", Toast.LENGTH_LONG).show();
                } else if (z < x) {
                    Toast.makeText(getActivity(), "End Date can't be before Start Date", Toast.LENGTH_LONG).show();
                } else {
                    generateReportData();
                }


                _email = txtEmail.getText().toString();
                if (answer.equals("email")) {
                    if (isValidEmail(_email)) {
                        /**
                         * Generate full report and Email
                         */
                        txtEmailBase
                                .setBackgroundResource(R.drawable.edittext_login_box_base);
                        if (mConnection) {
                            sendEmail();
                        } else {
                            Toast.makeText(getActivity(),
                                    "No Internet Connection", Toast.LENGTH_LONG)
                                    .show();
                        }

                    } else {
                        txtEmailBase.setBackgroundResource(R.drawable.error);
                        Toast.makeText(getActivity(), "Invalid Email",
                                Toast.LENGTH_LONG).show();
                    }
                } else {
                    /**
                     * Generate full report and save to phone
                     */
                    File tFile = generateTempReportFile();
                    if (tFile != null) {
                        // copy file to SD Card
                        if (isExternalStorageWritable()) {
                            // create new dir TaxiClerk
                            String sd_dir = "/sdcard/";
                            String new_dir = "/TaxiClerk/";

                            File mDir = new File(sd_dir, new_dir);
                            mDir.mkdirs();

                            // create new report file
                            String new_file = today + "_report.txt";
                            File outputFile = new File(mDir, new_file);

                            // copy cache file to report file
                            if (copyFile(tFile, outputFile)) {
                                Toast.makeText(getActivity(), "Report created",
                                        Toast.LENGTH_LONG).show();
                                tFile.delete();
                                // opening file for view
                              try {
                                  String myPath = Environment.getExternalStorageDirectory().getAbsolutePath() + new_dir +  new_file;
                                  Uri startDir = Uri.fromFile(new File(myPath));
                                  Intent intent = new Intent(Intent.ACTION_VIEW);
                                  intent.setDataAndType(startDir, "text/plain");
                                  startActivity(intent);
                              } catch (Exception ex) {
                                  ex.printStackTrace();
                              }


                            } else {
                                Toast.makeText(getActivity(),
                                        "Saving report to SD Card failed",
                                        Toast.LENGTH_LONG).show();
                            }

                        } else {
                            Toast.makeText(getActivity(),
                                    "No SD Card available", Toast.LENGTH_LONG)
                                    .show();
                        }

                    } else {
                        Toast.makeText(getActivity(),
                                "Failed to create report file",
                                Toast.LENGTH_LONG).show();
                    }
                }
            }

        });
        return rootView;
    }

    /**
     * Check for connection.
     *
     * @return the boolean
     */
    private Boolean checkConnection() {
        ConnectivityManager manager = (ConnectivityManager) getActivity()
                .getSystemService(getActivity().CONNECTIVITY_SERVICE);
        // For 3G check
        boolean is3g = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
                .isConnectedOrConnecting();
        // For WiFi Check
        boolean isWifi = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
                .isConnectedOrConnecting();


        return is3g || isWifi;
    }

    /**
     * Send email.
     */
    protected void sendEmail() {
        File tFile = generateTempReportFile();
        if (tFile != null) {
            // copy file to SD Card
            if (isExternalStorageWritable()) {
                // create new dir TaxiClerk
                String sd_dir = "/sdcard/";
                String new_dir = "/TaxiClerk/";

                File mDir = new File(sd_dir, new_dir);
                mDir.mkdirs();

                // create new report file
                String new_file = today + "_report.txt";
                File outputFile = new File(mDir, new_file);

                // copy cache file to report file
                if (copyFile(tFile, outputFile)) {
                    Toast.makeText(getActivity(), "Report created",
                            Toast.LENGTH_LONG).show();
                    tFile.delete();
                    URI_att = Uri.parse("file://" + outputFile.toString());

                    // Log.d("Att", URI.toString());
                } else {
                    Toast.makeText(getActivity(), "Saving report failed",
                            Toast.LENGTH_LONG).show();
                }

            } else {
                Toast.makeText(getActivity(), "No SD Card available",
                        Toast.LENGTH_LONG).show();
            }

        } else {
            Toast.makeText(getActivity(), "Failed to create report file",
                    Toast.LENGTH_LONG).show();
        }

        /*
      The subject.
     */
        String subject = "Taxi Clerk report";
        /*
      The body.
     */
        String body = "Thank you for using Taxi Clerk\n\nPlease find attached copy of your full report.\n\nTaxi Clerk";
        email(_email, subject, body, URI_att);

    }

    /**
     * Email.
     *
     * @param to         the to
     * @param subject    the subject
     * @param body       the body
     * @param attachment the attachment
     */
    public void email(String to, String subject, String body, Uri attachment) {
        Intent chooser;
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent = new Intent(Intent.ACTION_SEND);
        intent.putExtra(Intent.EXTRA_EMAIL, new String[]{to});
        intent.putExtra(Intent.EXTRA_STREAM, attachment);
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, body);
        intent.setType("message/rfc822");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            startActivity(intent);
        } else {
            chooser = Intent.createChooser(intent, "Send Email");
            startActivity(chooser);
        }

    }

    /**
     * Copy file.
     *
     * @param sourceFile the source file
     * @param outputFile the output file
     * @return true, if successful
     */
    public static boolean copyFile(File sourceFile, File outputFile) {
        try {
            File sd = Environment.getExternalStorageDirectory();
            if (sd.canWrite()) {

                if (!outputFile.exists()) {
                    outputFile.createNewFile();
                }
                if (sourceFile.exists()) {
                    InputStream src = new FileInputStream(sourceFile);
                    OutputStream dst = new FileOutputStream(outputFile);
                    // Copy the bits from instream to outstream
                    byte[] buf = new byte[1024];
                    int len;
                    while ((len = src.read(buf)) > 0) {
                        dst.write(buf, 0, len);
                    }
                    src.close();
                    dst.close();
                }
            }
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

    /**
     * Generate temp report file.
     *
     * @return the file
     */
    protected File generateTempReportFile() {
        int len = 20;
        String separator = "\r\n";

        try {
            File outputDir = getActivity().getCacheDir();

            File outputFile = new File(outputDir, today + "_report.txt");

            // write it
            BufferedWriter bw = new BufferedWriter(new FileWriter(outputFile));

            bw.append(getResources().getString(R.string.report) + "       " + startDateToDisplay + " to " + endDateToDisplay);
            bw.append(separator);
            bw.append(separator);
            bw.append(fixLength(getResources().getString(R.string.total_jobs), len) + allJobs);
            bw.append(separator);
            bw.append(fixLength(getResources().getString(R.string.hours_total), len) + allHours);
            bw.append(separator);
            bw.append(separator);


            bw.append(fixLength(getResources().getString(R.string.income_total), len) + euro + incomeTotal);
            bw.append(separator);
            for (int i = 0; i < 40; i++) {
                bw.append("-");
            }
            bw.append(separator);

            // Cash income
            bw.append(getResources().getString(R.string.cash));
            bw.append(separator);
            bw.append("\t" + fixLength(getResources().getString(R.string.street), len) + euro + cashProvider1);
            bw.append(separator);
            if (!pr2.isEmpty()) {
                bw.append("\t" + fixLength(pr2, len) + euro + cashProvider2);
                bw.append(separator);
            }
            if (!pr3.isEmpty()) {
                bw.append("\t" + fixLength(pr3, len) + euro + cashProvider3);
                bw.append(separator);
            }
            bw.append(separator);
            bw.append(separator);

            // Acounts income
            bw.append(getResources().getString(R.string.account));
            bw.append(separator);
            if (!pr2.isEmpty()) {
                bw.append("\t" + fixLength(pr2, len) + euro + accProvider2);
                bw.append(separator);
            }
            if (!pr3.isEmpty()) {
                bw.append("\t" + fixLength(pr3, len) + euro + accProvider3);
                bw.append(separator);
            }
            bw.append(separator);
            bw.append(separator);
            bw.append(separator);
            // Expenses
            bw.append(fixLength(getResources().getString(R.string.expense_total), len) + euro + expenseTotal);
            bw.append(separator);
            for (int i = 0; i < 40; i++) {
                bw.append("-");
            }
            bw.append(separator);

            bw.append(getResources().getString(R.string.diesel));
            bw.append(separator);
            bw.append("\t" + fixLength(getResources().getString(R.string.cash), len) + euro + dieselCash);
            bw.append(separator);
            bw.append("\t" + fixLength(getResources().getString(R.string.card), len) + euro + dieselAcc);
            bw.append(separator);
            bw.append("\t" + fixLength(getResources().getString(R.string.acc), len) + euro + dieselBankAcc);
            bw.append(separator);
            bw.append(separator);

            bw.append(getResources().getString(R.string.maintenance));
            bw.append(separator);
            bw.append("\t" + fixLength(getResources().getString(R.string.cash), len) + euro + maintCash);
            bw.append(separator);
            bw.append("\t" + fixLength(getResources().getString(R.string.card), len) + euro + maintAcc);
            bw.append(separator);
            bw.append("\t" + fixLength(getResources().getString(R.string.acc), len) + euro + maintBankAcc);
            bw.append(separator);
            bw.append(getAllExpenseByType("maintenance"));
            bw.append(separator);
            bw.append(separator);

            bw.append(getResources().getString(R.string.insurance));
            bw.append(separator);
            bw.append("\t" + fixLength(getResources().getString(R.string.cash), len) + euro + insCash);
            bw.append(separator);
            bw.append("\t" + fixLength(getResources().getString(R.string.card), len) + euro + insAcc);
            bw.append(separator);
            bw.append("\t" + fixLength(getResources().getString(R.string.acc), len) + euro + insBankAcc);
            bw.append(separator);
            bw.append(getAllExpenseByType("insurance"));
            bw.append(separator);
            bw.append(separator);

            bw.append(getResources().getString(R.string.base));
            bw.append(separator);
            bw.append("\t" + fixLength(getResources().getString(R.string.cash), len) + euro + baseCash);
            bw.append(separator);
            bw.append("\t" + fixLength(getResources().getString(R.string.card), len) + euro + baseAcc);
            bw.append(separator);
            bw.append("\t" + fixLength(getResources().getString(R.string.acc), len) + euro + baseBankAcc);
            bw.append(separator);
            bw.append(getAllExpenseByType("base"));
            bw.append(separator);
            bw.append(separator);

            bw.append(getResources().getString(R.string.phone));
            bw.append(separator);
            bw.append("\t" + fixLength(getResources().getString(R.string.cash), len) + euro + phoneCash);
            bw.append(separator);
            bw.append("\t" + fixLength(getResources().getString(R.string.card), len) + euro + phoneAcc);
            bw.append(separator);
            bw.append("\t" + fixLength(getResources().getString(R.string.acc), len) + euro + phoneBankAcc);
            bw.append(separator);
            bw.append(getAllExpenseByType("phone"));
            bw.append(separator);
            bw.append(separator);

            bw.append(getResources().getString(R.string.misc));
            bw.append(separator);
            bw.append("\t" + fixLength(getResources().getString(R.string.cash), len) + euro + miscCash);
            bw.append(separator);
            bw.append("\t" + fixLength(getResources().getString(R.string.card), len) + euro + miscAcc);
            bw.append(separator);
            bw.append("\t" + fixLength(getResources().getString(R.string.acc), len) + euro + miscBankAcc);
            bw.append(separator);
            bw.append(getAllExpenseByType("misc"));
            bw.append(separator);
            bw.append(separator);

            bw.append(getResources().getString(R.string.mileage));
            bw.append(separator);
            bw.append("\t" + fixLength(getResources().getString(R.string.mileage_start), len) + minMileage);
            bw.append(separator);
            bw.append("\t" + fixLength(getResources().getString(R.string.mileage_run), len) + difMileage);


            Toast.makeText(getActivity(), "Done saving file",
                    Toast.LENGTH_SHORT).show();

            bw.close();
            return outputFile;

        } catch (Exception e) {
            // TODO: handle exception
        }
        return null;
    }

    /**
     * Fix length.
     *
     * @param string the string
     * @param i      the i
     * @return the string
     */
    protected String fixLength(String string, int i) {
        String res = null;
        int len = string.length();
        if (len < i) {
            StringBuilder sb = new StringBuilder(string);

            char[] ch = new char[i - len];
            Arrays.fill(ch, '_');
            sb.append(ch);
            res = sb.toString();
        } else {
            res = string;
        }

        return res;
    }

    /**
     * Generate rport data.
     */
    private void generateReportData() {

        DatabaseHandler db = new DatabaseHandler(getActivity(), null);
        DecimalFormat dec = new DecimalFormat("0.00");

        /**
         * All Jobs
         */
        allJobs = getRecordTotalJobs();

        /**
         * All Hours
         */
        allHours = getRecordTotalHr();
        allHours = correctTheTime(String.valueOf(allHours));

        /**
         * Total Cash
         */
        incomeTotal = getRecordTotalIncome();

        List<Provider> prov = db.getProvidersState();
        for (Provider rec : prov) {
            if (rec.getID() == 1) {
                /*
      The pr1.
     */
                String pr1 = rec.getName();
                cashProvider1 = getRecordInc(pr1, "cash");
            }
            if (rec.getID() == 2) {
                pr2 = rec.getName();
                cashProvider2 = getRecordInc(pr2, "cash");
                accProvider2 = getRecordInc(pr2, "account");
            }
            if (rec.getID() == 3) {
                pr3 = rec.getName();
                cashProvider3 = getRecordInc(pr3, "cash");
                accProvider3 = getRecordInc(pr3, "account");
            }

        }
        db.close();

        /**
         * Expenses
         */
        /* Diesel */
        dieselCash = getRecordExp("diesel", "cash");
        dieselAcc = getRecordExp("diesel", "card");
        dieselBankAcc = getRecordExp("diesel", "bank acc");
        /* Maintanace */
        maintCash = getRecordExp("maintenance", "cash");
        maintAcc = getRecordExp("maintenance", "card");
        maintBankAcc = getRecordExp("maintenance", "bank acc");
		/* Insurance */
        insCash = getRecordExp("insurance", "cash");
        insAcc = getRecordExp("insurance", "card");
        insBankAcc = getRecordExp("insurance", "bank acc");
		/* Base */
        baseCash = getRecordExp("base", "cash");
        baseAcc = getRecordExp("base", "card");
        baseBankAcc = getRecordExp("base", "bank acc");
		/* Phone */
        phoneCash = getRecordExp("phone", "cash");
        phoneAcc = getRecordExp("phone", "card");
        phoneBankAcc = getRecordExp("phone", "bank acc");
		/* Misc */
        miscCash = getRecordExp("misc", "cash");
        miscAcc = getRecordExp("misc", "card");
        miscBankAcc = getRecordExp("misc", "bank acc");

		/* Min Max Milage */
        int startMileage = getMinMil();
        int currentMileage = getMaxMil();
        minMileage = String.valueOf(startMileage);
        difMileage = String.valueOf(currentMileage - startMileage);

        expenseTotal = String.valueOf(Double.valueOf(dieselCash)
                + Double.valueOf(dieselAcc) + Double.valueOf(dieselBankAcc)
                + Double.valueOf(maintCash) + Double.valueOf(maintBankAcc)
                + Double.valueOf(maintAcc) + Double.valueOf(insCash)
                + Double.valueOf(insAcc) + Double.valueOf(insBankAcc)
                + Double.valueOf(baseCash) + Double.valueOf(baseAcc)
                + Double.valueOf(baseBankAcc) + Double.valueOf(phoneCash)
                + Double.valueOf(phoneAcc) + Double.valueOf(phoneBankAcc)
                + Double.valueOf(miscCash) + Double.valueOf(miscAcc)
                + Double.valueOf(miscBankAcc));
        expenseTotal = dec.format(Float.parseFloat(expenseTotal));

        db.close();
    }


    /**
     * Get Max Mileage
     *
     * @return int res
     */
    private int getMaxMil() {
        DatabaseHandler db = new DatabaseHandler(getActivity(), null);
        int res = db.getMaxColumnData(startDateToSearch,endDateToSearch);
        db.close();
        return res;
    }

    /**
     * Get Min Mileage
     *
     * @return int res
     */
    private int getMinMil() {
        DatabaseHandler db = new DatabaseHandler(getActivity(), null);
        int res = db.getMinColumnData(startDateToSearch,endDateToSearch);
        db.close();
        return res;
    }


    /**
     * All misc records
     *
     * @return string
     */
    private String getAllExpenseByType(String type_name) {

        DatabaseHandler db = new DatabaseHandler(getActivity(), null);
        StringBuilder sb = new StringBuilder();

        List<Expense> misc = db.getAllExpenseForType(startDateToSearch,endDateToSearch,type_name);

        for (Expense rec : misc) {
            sb.append(rec.getDate() + "\t\t");
            sb.append(euro + " " + rec.getAmount() + "\t\t");
            sb.append(rec.getPaymentType() + "\t\t");
            sb.append(rec.getNotes() + "\n");
        }
        db.close();
        return sb.toString();
    }

    /**
     * Gets the all jobs.
     *
     * @return the all jobs
     */
    private String getRecordTotalJobs() {
        DatabaseHandler db = new DatabaseHandler(getActivity(), null);
        String res = String.valueOf(db.allJobsForPeriod(startDateToSearch,endDateToSearch));

        db.close();
        return res;
    }

    /**
     * Gets the all income.
     *
     * @return the all income
     */
    private String getRecordTotalIncome() {

        DatabaseHandler db = new DatabaseHandler(getActivity(), null);
        DecimalFormat dec = new DecimalFormat("0.00");

        String sql = "SELECT SUM(CAST(" + KEY_INC_AMOUNT
                + " as DECIMAL(9,2))) FROM " + TABLE_INCOME + " WHERE "
                + KEY_INC_ACTIVE + " LIKE 'yes' AND " + KEY_INC_DATE + " >= '" + startDateToSearch
                + "' AND " + KEY_INC_DATE + " <= '" + endDateToSearch + "'";
        String res = db.getAllIncomeTotal(sql);
        Float in = Float.parseFloat(res);

        db.close();
        return dec.format(in);
    }

    /**
     * Gets the all hours.
     *
     * @return the all hours
     */
    private String getRecordTotalHr() {

        DatabaseHandler db = new DatabaseHandler(getActivity(), null);
        DecimalFormat dec = new DecimalFormat("0.00");

        String sql = "SELECT SUM(CAST(" + KEY_HOURS
                + " as DECIMAL(9,2))) FROM " + TABLE_HOURS + " WHERE "
                + KEY_HOURS_ACTIVE + " LIKE 'yes' AND " + KEY_HOURS_DATE + " >= '" + startDateToSearch
                + "' AND " + KEY_HOURS_DATE + " <= '" + endDateToSearch + "'";
        String res = db.getAllIncomeTotal(sql);
        Float in = Float.parseFloat(res);

        db.close();
        return dec.format(in);
    }

    /**
     * Gets the all exp.
     *
     * @param name the name
     * @param type the type
     * @return the all exp
     */
    private String getRecordExp(String name, String type) {
        String res = null;
        String sql = null;
        DatabaseHandler db = new DatabaseHandler(getActivity(), null);
        DecimalFormat dec = new DecimalFormat("0.00");

        sql = "SELECT SUM(CAST(" + KEY_AMOUNT + " as DECIMAL(9,2))) FROM "
                + TABLE_EXPENSE + " WHERE " + KEY_ACTIVE + " LIKE 'yes' AND "
                + KEY_EXPENSE_NAME + " LIKE '" + name + "' AND "
                + KEY_PAYMENT_TYPE + " LIKE '" + type + "' AND " + KEY_DATE + " >= '" + startDateToSearch
                + "' AND " + KEY_DATE + " <= '" + endDateToSearch + "'";

        res = db.doSql(sql);
        Float in = Float.parseFloat(res);

        db.close();
        return dec.format(in);
    }

    /**
     * Gets the all income.
     *
     * @param name the name
     * @param type the type
     * @return the all exp
     */
    private String getRecordInc(String name, String type) {

        DatabaseHandler db = new DatabaseHandler(getActivity(), null);
        DecimalFormat dec = new DecimalFormat("0.00");

        String sql = "SELECT SUM(CAST(" + KEY_INC_AMOUNT
                + " as DECIMAL(9,2))) FROM " + TABLE_INCOME + " WHERE "
                + KEY_INC_ACTIVE + " LIKE 'yes' AND " + KEY_INC_TYPE
                + " LIKE '" + type + "' AND " + KEY_INC_PROVIDER + " LIKE '"
                + name + "' AND " + KEY_INC_DATE + " >= '" + startDateToSearch
                + "' AND " + KEY_INC_DATE + " <= '" + endDateToSearch + "'";

        String res = db.doSql(sql);
        // Float in = (float) 0;
        Float in = Float.parseFloat(res);

        db.close();
        return dec.format(in);
    }

    /**
     * Correct the time.
     *
     * @param valueOf the value of
     * @return the string
     */
    private String correctTheTime(String valueOf) {
        String res = null;
        String hr;
        String min;
        String str = new String(valueOf);
        String x = str.replaceAll("[^0-9]", "");

        if (x.length() > 3) {
            hr = x.substring(0, 2);
            min = x.substring(2, x.length());
        } else {
            hr = x.substring(0, 1);
            min = x.substring(1, x.length());
        }

		/*
		 * 
		 * Modify minutes to match 60min format
		 */
        int a = Integer.valueOf(min) - 60;
        int b = Integer.valueOf(hr);

        if (a > 0) {
            if (a < 10) {
                min = "0" + String.valueOf(a);
            } else {
                min = String.valueOf(a);
            }
            b = b + 1;
        }
        res = String.valueOf(b) + "." + min;

        return res;
    }

    /**
     * Email validity.
     *
     * @param target the target
     * @return true, if is valid email
     */
    protected boolean isValidEmail(String target) {
        return target != null && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }

    /**
     * Checks if external storage is available for read and write.
     *
     * @return true, if is external storage writable
     */
    public boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(state);
    }

    /**
     * Get current date.
     *
     * @return the string
     */
    private String setCurrentDate() {
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        c.set(year, month, day);

        sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
        return sdf.format(c.getTime());
    }
    /**
     * The Class DatePickerFragment.
     */
    public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

        /** The Constant START_DATE. */
        static final int START_DATE = 1;

        /** The Constant END_DATE. */
        static final int END_DATE = 2;

        /** The m chosen date. */
        private int mChosenDate;

        /** The cur. */
        int cur = 0;

        /** The sdf db. */
        private SimpleDateFormat sdfDB;

        /* (non-Javadoc)
         * @see android.support.v4.app.DialogFragment#onCreateDialog(android.os.Bundle)
         */
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {

            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            Bundle bundle = this.getArguments();
            if (bundle != null) {
                mChosenDate = bundle.getInt("DATE", 1);
            }

            switch (mChosenDate) {

                case START_DATE:
                    cur = START_DATE;
                    return new DatePickerDialog(getActivity(), this, year, month, day);

                case END_DATE:
                    cur = END_DATE;
                    return new DatePickerDialog(getActivity(), this, year, month, day);

            }
            return null;
        }

        /* (non-Javadoc)
         * @see android.app.DatePickerDialog.OnDateSetListener#onDateSet(android.widget.DatePicker, int, int, int)
         */
        @Override
        public void onDateSet(DatePicker datePicker, int year, int month, int day) {
            Calendar c = Calendar.getInstance();
            //int y = c.get(Calendar.YEAR);
            // setting current year, preventing the
            // user to pickup different year


            c.set(year, month, day);

            if (cur == START_DATE) {
                sdf = new SimpleDateFormat("dd-MMM", Locale.ENGLISH);
                sdfDB = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);

                startDateToSearch = sdfDB.format(c.getTime());

                startDateToDisplay = sdf.format(c.getTime());
                txtStartDateFullReport.setText(startDateToDisplay);

            } else {
                sdf = new SimpleDateFormat("dd-MMM", Locale.ENGLISH);
                sdfDB = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);

                endDateToSearch = sdfDB.format(c.getTime());

                endDateToDisplay = sdf.format(c.getTime());
                txtEndDateFullReport.setText(endDateToDisplay);
            }
        }

    }

}
