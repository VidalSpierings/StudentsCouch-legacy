package com.studentscouch.projectbostonfiles.models.implementers;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.util.Base64;
import android.view.View;
import android.widget.ImageView;

import com.studentscouch.projectbostonfiles.models.interfaces.ConfirmRegistrationActivityModelInterface;
import com.studentscouch.projectbostonfiles.observables.ConfirmRegistrationObservables;

import java.io.ByteArrayOutputStream;
import java.util.List;

public class ConfirmRegistrationActivityModel implements ConfirmRegistrationActivityModelInterface {

    private ConfirmRegistrationObservables observables;

    public ConfirmRegistrationActivityModel(ConfirmRegistrationObservables observables){

        this.observables = observables;

    }

    @Override
    public String convertBitmapToBase64StringLoop(List<ImageView> apartmentImages, int i) {

        Bitmap bitmap = ((BitmapDrawable) apartmentImages.get(i).getDrawable()).getBitmap();

        // get apartment image bitmap at index

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);

        byte[] b = byteArrayOutputStream.toByteArray();

        // convert baos object to byte array object

        return Base64.encodeToString(b, Base64.DEFAULT);

        // convert byte array object to Base64 encoded String
    }

    @Override
    public String convertBitmapToBase64String(Bitmap bitmap) {

        ByteArrayOutputStream byteArrayOutputStream1 = new ByteArrayOutputStream();

        // create baos object

        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream1);

        byte[] b1 = byteArrayOutputStream1.toByteArray();

        // convert baos object to byte array object

        return Base64.encodeToString(b1, Base64.DEFAULT);

    }

    @Override
    public void setObservablesFromSharedPrefs(SharedPreferences prefs) {

        observables.setYear(prefs.getInt("savedUserDataSharedBirthdateYear", 1998));

        observables.setMonth(prefs.getInt("savedUserDataSharedBirthdateMonth", 3));

        observables.setDay(prefs.getInt("savedUserDataSharedBirthdateDay", 7));

        // retrieve user's birth date

        observables.setGender(prefs.getString("savedUserDataSharedGender", "female"));

        // retrieve gender selected by user

    }

    @Override
    public int getYesOrNoSelected(Context context) {

        SharedPreferences prefs2 = context.getSharedPreferences("RegisterActivityScreen1YesNo", Context.MODE_PRIVATE);

        // retrieve data submitted by user during registration process and data on whether or not user has selected to register as a host

        return prefs2.getInt("Choicee", 5);

    }

    @Override
    public void getRemainingDataFromSharedPrefs(Context context, int choice) {

        SharedPreferences prefs = context.getSharedPreferences("savedUserData", Context.MODE_PRIVATE);

        observables.setProfile_picture(prefs.getString("profile_picture", ""));

        // retrieve apartment images in Base64 Sting format

        observables.setCountryCode(prefs.getString("savedUserDataSharedCountryCode", ""));

        observables.setProfile_image(prefs.getString("profile_picture", ""));

        observables.setApartementImage1(prefs.getString("apartement1", ""));

        observables.setGender(prefs.getString("savedUserDataSharedGender", "female"));

        observables.setLocationID(prefs.getString("savedUserDataPlaceID", ""));

    }

    @Override
    public void setApartmentObservables(Context context, int choice) {

        SharedPreferences prefs = context.getSharedPreferences("savedUserData", Context.MODE_PRIVATE);

        observables.setCountryCode(prefs.getString("savedUserDataSharedCountryCode", ""));

        observables.setProfile_image(prefs.getString("profile_picture", ""));

        observables.setApartementImage1(prefs.getString("apartement1", ""));

        observables.setGender(prefs.getString("savedUserDataSharedGender", "female"));

        observables.setLocationID(prefs.getString("savedUserDataPlaceID", ""));

    }

    @Override
    public void setApartmentData(Context context, SharedPreferences prefs) {

        observables.setCityVillage(prefs.getString("savedUserDataShared_city_or_village", null));
        observables.setStreet(prefs.getString("savedUserDataSharedStreet", null));
        observables.setHouse_number(prefs.getString("savedUserDataShared_house_number", null));
        observables.setTitle(prefs.getString("savedUserDataSharedTitle", null));
        observables.setDescription(prefs.getString("savedUserDataSharedDescription", null));
        observables.setPrice_per_night(prefs.getString("savedUserDataShared_price_per_night", ""));
        observables.setIs_two_people_allowed(prefs.getInt("savedUserDataIsTwoPeopleAllowed", 0));

        // retrieve specific types of submitted data about the users' apartment if possible

    }

    @Override
    public void loadApartmentImagesBecomeHostSelected(
            Context context, Bitmap bitmapApartement1, Bitmap bitmapApartement2,
            Bitmap bitmapApartement3, ImageView apartementImage1_1, ImageView apartementImage2,
            ImageView apartementImage3){

        SharedPreferences prefs = context.getSharedPreferences("savedUserData", Context.MODE_PRIVATE);

        // retrieve main data saved in sharedPreferences

        attemptRetrieveThirdApartmentImage2(prefs, apartementImage3);

        if (prefs.getString("apartement2", "") == "" && prefs.getString("apartement3", "") == "" ){

            arrangeA2nullA3null2(prefs, bitmapApartement1,
                    apartementImage1_1, apartementImage2, apartementImage3);

        }else if (prefs.getString("apartement2", "") != "" && prefs.getString("apartement3", "") == ""){

            arrangeA2notNullA3null2(prefs, bitmapApartement1, bitmapApartement2,
                    apartementImage1_1, apartementImage2, apartementImage3);

        }else if (prefs.getString("apartement2", "") == "" && prefs.getString("apartement3", "") != ""){

            arrangeA2nullA3notNull2(prefs, bitmapApartement1, bitmapApartement3,
                    apartementImage1_1, apartementImage2, apartementImage3);

        }else if (prefs.getString("apartement2", "") != "" && prefs.getString("apartement3", "") != ""){

            arrangeAllImgsNotNull(bitmapApartement1, bitmapApartement2, bitmapApartement3,
                    apartementImage1_1, apartementImage2, apartementImage3);

        }else if (prefs.getString("apartement1", "") != "" && prefs.getString("apartement2", "") == ""){

            arrangeA1notNullA2null2(prefs, bitmapApartement1,
                    apartementImage1_1, apartementImage2, apartementImage3);

        } else if (prefs.getString("apartement1", "") != "" && prefs.getString("apartement2", "") != ""){

            arrangeA1notNullA2notNull2(prefs, bitmapApartement1, bitmapApartement2,
                    apartementImage1_1, apartementImage2, apartementImage3);

        }

    }

    private void arrangeAllImgsNotNull(Bitmap bitmapApartement1, Bitmap bitmapApartement2, Bitmap bitmapApartement3,
                                       ImageView apartementImage1_1, ImageView apartementImage2, ImageView apartementImage3){

        apartementImage1_1.setImageBitmap(bitmapApartement1);
        apartementImage2.setImageBitmap(bitmapApartement2);
        apartementImage3.setImageBitmap(bitmapApartement3);

        apartementImage1_1.setVisibility(View.VISIBLE);
        apartementImage3.setVisibility(View.VISIBLE);
        apartementImage2.setVisibility(View.VISIBLE);

        /*

         * if only first and second apartment images can be retrieved, empty third apartment slot
         * in sharedPreferences and set visibility of third imageView to gone

         */

        /*

         * arrangement of images as now as follows:

         * first imageView: empty
         * second imageView: filled (apartment image 1)
         * third imageView: filled (apartment image 2)

         */

    }

    @SuppressLint("CommitPrefEdits")
    private void arrangeA1notNullA2notNull2(SharedPreferences prefs, Bitmap bitmapApartement1, Bitmap bitmapApartement2,
                                            ImageView apartementImage1_1, ImageView apartementImage2, ImageView apartementImage3){

        prefs.edit().remove("apartement3");

        apartementImage2.setImageBitmap(bitmapApartement1);
        apartementImage3.setImageBitmap(bitmapApartement2);

        makeAllImagesVisible(apartementImage1_1, apartementImage2, apartementImage3);

        /*

         * if only first and second apartment images can be retrieved, empty third apartment slot
         * in sharedPreferences and set visibility of third imageView to gone

         */

        /*

         * arrangement of images as now as follows:

         * first imageView: empty
         * second imageView: filled (apartment image 1)
         * third imageView: filled (apartment image 2)

         */

    }

    private void makeAllImagesVisible(ImageView apartementImage1_1, ImageView apartementImage2, ImageView apartementImage3){

        apartementImage3.setVisibility(View.VISIBLE);
        apartementImage2.setVisibility(View.VISIBLE);
        apartementImage1_1.setVisibility(View.VISIBLE);

    }

    private void attemptRetrieveThirdApartmentImage2(SharedPreferences prefs, ImageView apartementImage3){

        try{

            String previouslyEncodedImage4 = prefs.getString("apartement3", "");
            byte[] bitmap_byte_apartment3 = Base64.decode(previouslyEncodedImage4, Base64.DEFAULT);
            Bitmap bitmapApartement3 = BitmapFactory.decodeByteArray(bitmap_byte_apartment3, 0, bitmap_byte_apartment3.length);

            apartementImage3.setImageBitmap(bitmapApartement3);

            /*

             * convert Base64 String object of third apartment image
             * saved in sharedPreferences into bitmap object.
             * if no apartment image is found, return an empty String

             */

        }catch(Exception e){

            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("apartement3", "");
            editor.apply();

            apartementImage3.setVisibility(View.GONE);

            /*

             * if conversion of third apartment image fails, put empty String
             * in second apartment image slot and make the third apartment imageView disappear in layout

             */

        }

    }

    @SuppressLint("CommitPrefEdits")
    private void arrangeA1notNullA2null2(SharedPreferences prefs, Bitmap bitmapApartement1,
                                         ImageView apartementImage1_1, ImageView apartementImage2, ImageView apartementImage3){

        prefs.edit().remove("apartement2");

        prefs.edit().apply();

        /*

         * if only first apartment image can be retrieved, empty second apartment slot
         * in sharedPreferences and set visibility of third imageView to gone

         */

        apartementImage2.setImageBitmap(bitmapApartement1);

        /*

         * arrangement of images as now as follows:

         * first imageView: empty
         * second imageView: filled (apartment image 1)
         * third imageView: empty

         */

        makeAllButSecondImageInvisible(apartementImage1_1, apartementImage2, apartementImage3);

    }

    private void makeAllButSecondImageInvisible(ImageView apartementImage1_1, ImageView apartementImage2, ImageView apartementImage3){

        apartementImage1_1.setVisibility(View.GONE);
        apartementImage2.setVisibility(View.VISIBLE);
        apartementImage3.setVisibility(View.GONE);

    }

    @SuppressLint("CommitPrefEdits")
    private void arrangeA2nullA3notNull2(SharedPreferences prefs, Bitmap bitmapApartement1, Bitmap bitmapApartement3,
                                         ImageView apartementImage1_1, ImageView apartementImage2, ImageView apartementImage3){

        // apartment image found at slot 1 and 3

        prefs.edit().remove("apartement3");

        prefs.edit().apply();

        /*

         * if third apartment image string is empty,
         * remove apartment images slot in sharedPreferences

         */

        apartementImage2.setImageBitmap(bitmapApartement1);

        // insert first apartment image in second imageView

        apartementImage3.setImageBitmap(bitmapApartement3);

        // insert third apartment image in second imageView

        makeAllButFirstImageVisible(apartementImage1_1, apartementImage2, apartementImage3);

        /*

         * arrangement of images as now as follows:

         * first imageView: empty
         * second imageView: filled (apartment image 1)
         * third imageView: filled (apartment image 3)

         */

    }

    private void makeAllButFirstImageVisible(ImageView apartementImage1_1, ImageView apartementImage2, ImageView apartementImage3){

        apartementImage1_1.setVisibility(View.GONE);
        apartementImage2.setVisibility(View.VISIBLE);
        apartementImage3.setVisibility(View.VISIBLE);

    }

    @SuppressLint("CommitPrefEdits")
    private void arrangeA2notNullA3null2(SharedPreferences prefs, Bitmap bitmapApartement1, Bitmap bitmapApartement2,
                                         ImageView apartementImage1_1, ImageView apartementImage2, ImageView apartementImage3){

        // apartment image found at slot 2

        prefs.edit().remove("apartement3");

        prefs.edit().apply();

        /*

         * if third apartment image string is empty,
         * remove apartment images slot in sharedPreferences

         */

        apartementImage2.setImageBitmap(bitmapApartement1);

        // insert first apartment image into second imageView

        apartementImage3.setImageBitmap(bitmapApartement2);

        // insert second apartment image in third imageView

        makeAllButFirstImageVisible(apartementImage1_1, apartementImage2, apartementImage3);

        /*

         * arrangement of images as now as follows:

         * first imageView: empty
         * second imageView: filled (apartment image 1)
         * third imageView: filled (apartment image 2)

         */

    }

    @SuppressLint("CommitPrefEdits")
    private void arrangeA2nullA3null2(SharedPreferences prefs, Bitmap bitmapApartement1,
                                      ImageView apartementImage1_1, ImageView apartementImage2, ImageView apartementImage3){

        prefs.edit().remove("apartement2");
        prefs.edit().remove("apartement3");

        prefs.edit().apply();

        /*

         * if second and third apartment image strings are empty,
         * remove apartment images at their corresponding slots in sharedPreferences

         */

        apartementImage2.setImageBitmap(bitmapApartement1);

        makeAllButSecondImageInvisible(apartementImage1_1, apartementImage2, apartementImage3);


        /*

         * arrangement of images as now as follows:

         * first imageView: empty
         * second imageView: filled (apartment image 1)
         * third imageView: empty

         */

    }

}
