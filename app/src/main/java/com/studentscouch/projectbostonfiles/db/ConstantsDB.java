package com.studentscouch.projectbostonfiles.db;

public class ConstantsDB {

    public static final String APARTMENTS_TABLE = "Apartements";
    public static final String APARTMENT_AID_TABLE = "AID";
    public static final String APARTMENT_CITY_VILLAGE_TABLE = "city_or_village";
    public static final String APARTMENT_TITLE_TABLE = "title";
    public static final String APARTMENT_DESCRIPTION_TABLE = "description";
    public static final String APARTMENT_HOUSE_NUMBER_TABLE = "house_number";
    public static final String APARTMENT_LOCATION_ID_TABLE = "locationID";
    public static final String APARTMENT_STREET_TABLE = "street";
    public static final String APARTMENT_PRICE_PER_NIGHT_TABLE = "price_per_night";
    public static final String APARTMENT_IS_TWO_PEOPLE_ALLOWED_TABLE = "isTwoPeopleAllowed";
    public static final String APARTMENT_SUBLEASED_NIGHTS_LEFT_TABLE = "subleasedNightsLeft";
    public static final String APARTMENT_IBAN_NUMBER_TABLE = "IBAN";
    public static final String APARTMENT_HOST_STATUS_TABLE = "hostStatus";
    public static final String APARTMENT_IS_DIALOG_READ_TABLE = "isDialogRead";
    public static final String APARTMENT_COUNTRY_CODE_TABLE = "countryCode";
    public static final String APARTMENT_IS_IBAN_ADDED_TABLE = "isIbanAdded";
    public static final String APARTMENT_SUBLEASED_NIGHTS_NA_NON_IBAN = "123456789";
    public static final String APARTMENT_IS_CITY_REGULATIONS_DIALOG_READ_TABLE = "isCityRegulationsDialogRead";
    public static final String APARTMENT_STATUS_TABLE = "status";
    public static final String APARTMENT_NUM_RATINGS_TABLE = "ratings/ratingVariables/numRatings";
    public static final String APARTMENT_RATING_AVERAGE_TABLE = "ratings/ratingVariables/ratingAverage";

    public static final String APARTMENT_IMAGE_LOOP_TABLE = "apartment";
    public static final String APARTMENT_IMAGE_1_TABLE = "apartment1";
    public static final String APARTMENT_IMAGE_2_TABLE = "apartment2";
    public static final String APARTMENT_IMAGE_3_TABLE = "apartment3";

    // apartments table related variables

    public static final String UID_TABLE = "UID";

    public static final String EMAILS_TABLE = "emails";

    public static final String USER_DATA_TABLE = "UserData";

    public static final String NEEDS_UPDATE_TABLE = "needsUpdate";

    public static final String EU_BLOCK_TABLE = "BlockedCountries/EU_Block";

    public static final String EU_BLOCK_TABLE2 = "EU_Block";

    public static final String IBAN_KEY_MIN_UNIQUE_ID = "IBAN_key_";

    // misc variables

    public static final String USERS_TABLE = "Users";
    public static final String USER_FIRST_NAME_TABLE = "firstName";
    public static final String USER_LAST_NAME_TABLE = "lastName";
    public static final String USER_EMAIL_TABLE = "email";
    public static final String USER_GENDER_TABLE = "gender";
    public static final String USER_BIRTHDATE_TABLE = "birthdate";
    public static final String USER_BIRTHDATE_YEAR_TABLE = "birthDateYear";
    public static final String USER_BIRTHDATE_MONTH_TABLE = "birthDateMonth";
    public static final String USER_BIRTHDATE_DAY_TABLE = "birthDateDay";
    public static final String USER_PLACE_OF_RESIDENCE_TABLE = "place_of_residence";
    public static final String USER_PROFILE_IMAGE_TABLE = "profile_image";
    public static final String USER_EMOJI_RATING_TABLE = "emojiRating";
    public static final String USER_HAPPY_EMOJIS_RATING_TABLE = "happyEmojis";
    public static final String USER_UNHAPPY_EMOJIS_RATING_TABLE = "unhappyEmojis";
    public static final String USER_WARNINGS_TABLE = "warnings";
    public static final String USER_NUM_WARNINGS_TABLE = "numWarnings";
    public static final String USER_TIME_TABLE = "time";
    public static final String USER_IS_UNLOCKED_AGAIN_TABLE = "isUnlockedAgain";
    public static final String USER_MONTH_TABLE = "month";

    // users related tables

    public static final String GENDER_MALE = "male";
    public static final String GENDER_FEMALE = "female";

    public static final String HOST_STATUS_USER_IS_HOST = "isHost";
    public static final String HOST_STATUS_INFO_NOT_ADDED = "info_not_added";
    public static final String HOST_STATUS_NOT_A_HOST_ADDED = "not_a_host";

    public static final String BOOKING_ACCEPTED_PROGRESS_1 = "1";
    public static final String BOOKING_ACCEPTED_PROGRESS_2 = "2";

    public static String BOOKING_HOST = "HOST";
    public static String BOOKING_GUEST = "GUEST";

    public static String BOOKING_EIGHT_TO_ELEVEN = "EIGHT_TO_ELEVEN";
    public static String BOOKING_TWELVE_TO_FOUR = "TWELVE_TO_FOUR";
    public static String BOOKING_FIVE_TO_TEN = "FIVE_TO_TEN";

    public static final String BOOKING_STAY_TYPE_ROOM = "room";
    public static final String BOOKING_STAY_TYPE_APARTEMENT = "apartement";

    public static final String NUM_USERS_TABLE = "numUsers";
    public static final String NUM_HOSTS_TABLE = "numHosts";

    // database variables values

    public static final String BOOKINGS_TABLE = "bookings";
    public static final String BOOKING_ID_TABLE = "bookingID";
    public static final String BOOKING_NUM_DAYS_STAY_TABLE = "numDaysStay";
    public static final String BOOKING_START_DATE_DAY_TABLE = "startDateDay";
    public static final String BOOKING_START_DATE_MONTH_TABLE = "startDateMonth";
    public static final String BOOKING_START_DATE_YEAR_TABLE = "startDateYear";
    public static final String BOOKING_BOOKER_UID_TABLE = "bookerUID";
    public static final String BOOKING_IS_ACCEPTED_TABLE = "isAccepted";
    public static final String BOOKING_IS_ACCEPTED_2_TABLE = "isAccepted2";
    public static final String BOOKING_IS_STAY_FINISHED_HOST_DIALOG_READ_TABLE = "isStayFinishedHostDialogRead";
    public static final String BOOKING_FIRST_NAME_HOST_TABLE = "firstNameHost";
    public static final String BOOKING_LAST_NAME_HOST_TABLE = "lastNameHost";
    public static final String BOOKING_FIRST_NAME_GUEST_TABLE = "firstNameGuest";
    public static final String BOOKING_LAST_NAME_GUEST_TABLE = "lastNameGuest";
    public static final String BOOKING_FINISH_DATE_DAY_TABLE = "finishDateDay";
    public static final String BOOKING_FINISH_DATE_MONTH_TABLE = "finishDateMonth";
    public static final String BOOKING_FINISH_DATE_YEAR_TABLE = "finishDateYear";
    public static final String BOOKING_CITY_VILLAGE_TABLE = "cityVillage";
    public static final String BOOKING_IS_OFFICIAL_TABLE = "isOfficial";
    public static final String BOOKING_IS_DIALOG_READ_2_TABLE = "isDialogRead2";
    public static final String BOOKING_MONEY_2_TABLE = "money";
    public static final String BOOKING_TOTAL_PRICE_TABLE = "totalPrice";
    public static final String BOOKING_MONEY_COMISSION_GUEST_TABLE = "moneyComissionGuest";
    public static final String BOOKING_MONEY_COMISSION_HOST_TABLE = "moneyComissionHost";
    public static final String BOOKING_TOTAL_SC_EARNINGS_TABLE = "TotalSCearnings";
    public static final String BOOKING_LOCATION_NAME_EARNINGS_TABLE = "locationName";
    public static final String BOOKING_TIME_OF_ARRIVAL_TABLE = "time_of_arrival";
    public static final String BOOKING_IS_GUEST_FINISHED_TABLE = "isGuestFinished";
    public static final String BOOKING_TRANSACTION_ID_TABLE = "transaction_id";
    public static final String BOOKING_ROOM_OR_APARTMENT_TABLE = "roomOrApartement";

    // bookings table related variables

    public static final String TRANSACTIONS_TABLE = "Transactions";

    public static final String ITEMS_TABLE = "items";
    public static final String ITEM_APARTMENT_PRICE_TABLE = "price";
    public static final String ITEM_APARTMENT_PROFILE_IMAGE_TABLE = "profileId";
    public static final String ITEM_VISIBILITY_TABLE = "visibility";
    public static final String ITEM_AVERAGE_RATING_TABLE = "rating";
    public static final String ITEM_FIRST_NAME_HOST_TABLE = "name";

    // items table related variables

    public static final String USERS_TABLE_URL_REFERENCE = "Users/";
    public static final String AID_URL_REFERENCE = "/AID";
    public static final String APARTMENTS_TABLE_URL_REFERENCE = "Apartements/";
    public static final String ITEMS_TABLE_URL_REFERENCE = "items/";
    public static final String APARTMENT_LOCATION_ID_TABLE_URL_REFERENCE = "/locationID";
    public static final String BOOKINGS_TABLE_URL_REFERENCE = "/bookings";
    public static final String APARTMENT_SUBLEASED_NIGHTS_LEFT_TABLE_URL_REFERENCE = "/subleasedNightsLeft";
    public static final String APARTMENT_PRICE_PER_NIGHT_TABLE_URL_REFERENCE = "/price_per_night";
    public static final String USER_PROFILE_IMAGE_TABLE_URL_REFERENCE = "/profile_image";
    public static final String IS_APP_AVAILABLE_NON_HOSTS_TABLE = "isAppUnlocked/isAvailableNonHosts";
    public static final String USER_COUNTRY_CODE_URL_REFERENCE = "/countryCode";
    public static final String USER_WARNINGS_URL_REFERENCE = "/countryCode";
    public static final String WARNINGS_URL_REFERENCE = "/warnings";
    public static final String USER_WARNINGS_MONTH_URL_REFERENCE = "/warnings/month";
    public static final String USER_WARNINGS_IS_DIALOG_READ_URL_REFERENCE = "/warnings/isDialogRead";
    public static final String BLOCKED_COUNTRIES_URL_REFERENCE = "/BlockedCountries";
    public static final String HOST_STATUS_URL_REFERENCE = "/hostStatus";
    public static final String STATUS_URL_REFERENCE = "/status";
    public static final String STATUS2_URL_REFERENCE = "/hostStatus/status";
    public static final String TRANSACTIONS_URL_REFERENCE = "Transactions/";
    public static final String IS_CITY_REGULATIONS_DIALOG_READ_URL_REFERENCE = "/isCityRegulationsDialogRead";
    public static final String UNLOCKED_COUNTRIES_URL_REFERENCE = "isAppUnlocked/UnlockedCountries";
    public static final String IS_APP_UNLOCKED_URL_REFERENCE = "isAppUnlocked/isUnlocked";
    public static final String IS_APP_UNDER_CONSTRUCTION_URL_REFERENCE = "isAppUnlocked/isUnderConstruction";
    public static final String WANTS_TO_DELETE_URL_REFERENCE = "wantsToRemove";
    public static final String CANCELLED_BOOKINGS_URL_REFERENCE = "cancelledBookings";
    public  static final String ALL_RATING_URL_REFERENCE = "/ratings/all_ratings";
    public  static final String EMOJI_RATING_URL_REFERENCE = "/emojiRating";
    public  static final String IS_GUEST_FINISHED_URL_REFERENCE = "/isGuestFinished";
    public  static final String NUMBERS_URL_REFERENCE = "numbers";

    // database sub-paths

    public static final String NULL = "null";
    public static final String ERROR = "ERROR";
    public static final String NO_IBAN_ADDED = "NO_IBAN_ADDED";
    public static final String INVALID_APARTMENT = "DELETE_THIS_DATA";
    public static final String UNKNOWN_UID = "unknownUID";

    public static final String SUBLEASED_NIGHTS_NA_VALUE = "NO_IBAN_ADDED";

    // database void and error values

    public static final String TRUE = "true";

    public static final String IS_NOT_READ = "0";
    public static final String IS_READ = "1";

    public static final int EIGHT_TO_ELEVEN_VAL = 1;
    public static final int TWELVE_TO_FOUR_VAL = 2;
    public static final int FIVE_TO_TEN_VAL = 3;

    public static final String DONT_SHOW = "0";
    public static final String SHOW = "1";
    public static final String SHOW_SEPCIAL = "2";

    public static final String NO = "0";
    public static final String YES = "1";

    // database switch values

}
