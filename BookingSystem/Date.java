package BookingSystem;


public class Date {
    public static final String MONDAY = "mandag";
    public static final String TUESDAY = "tirsdag";
    public static final String WEDNESDAY = "onsdag";
    public static final String THURSDAY = "torsdag";
    public static final String FRIDAY = "fredag";
    public static final String SATURDAY = "lørdag";
    public static final String SUNDAY = "søndag";

    public static final String[] WEEKDAYNAMES = {SUNDAY, MONDAY,TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY};

    // For day and month could probably use byte, due to those will never exceed 31 and 12
    // Narrower datatypes gives better performance.
    private int day;
    private int month;
    private int year;

    public Date(int day, int month, int year) {
        this.day = day;
        this.month = month;
        this.year = year;
    }

    public int getDay() {
        return this.day;
    }

    public int getMonth() {
        return this.month;
    }

    public int getYear() {
        return this.year;
    }

    public static boolean isLeapYear(int year) {
        if (year % 100 == 0) {
            return year % 400 == 0;
        }
        return year % 4 == 0;
    };

    public static int[] getYearDaysInMonths(int year) {
        int[] daysInMonths = {31, (isLeapYear(year) ? 29 : 28), 31, 30, 31, 30, 31, 31, 30, 31, 30 ,31};
        return daysInMonths;
    };

    /**
     * 
     * Old approach, would use day since date
     * Also slow with algorithm due to alot of loops, weekday can be calcualted instead
     * But would have a lot off asumptions and would not work well with days before the given date
    public int daysSinceDate(Date date) {
        if (date.equals(this)) {
            // early return
            return 0;
        }
        // If in same month and year, just use simply calulcation to avoid rest of code
        if (date.getYear() == this.year && date.getMonth() == this.month) {
            return this.day - date.getDay();
        }
        int days = 0;
        int year = this.year;
        int month = this.month;
    
        // skip whole years, but stop at 1 year above for avoiding edge cases
        while ((year > (date.getYear()+1))) {
            days += (isLeapYear(year)) ? 366 : 365;
            year--;
        }

        // go back months
        int[] yearMonths = getYearDaysInMonths(year);
        while (!(year == date.getYear() && (month == date.getMonth()+1))) {
            days+=yearMonths[month-2 >= 0 ? month-2 : (12 + (month-2))];   
            month--;
            if(month==0) {
                month=12;

                yearMonths = getYearDaysInMonths(--year);
            }
        }
        days+= this.day +  (yearMonths[date.getMonth()-1] - date.getDay());
        return days;
    };
    public int getCurrentDayNumber() {
        Not good to assumption of start date
        // Will here assume that every day is after 01.01.2024;
        return this.daysSinceDate(Date.currentDate()) % 7;
    };
        public String getCurrentDay() {
        return WEEK[getCurrentDayNumber()];
    }
    /* 
    public String getWeekDayInAmountOfDays(int days) {
        return WEEK[this.daysSinceDate(Date.currentDate()) + days % 7];
    };
    */

    public int getCurrentDayNumber() {
        // Method and formula from https://artofmemory.com/blog/how-to-calculate-the-day-of-the-week/
        int yy = this.year % 100;
        int yearCode = (yy + Math.floorDiv(yy, 4)) % 7;
        int[] monthCode = {0,3,3,6,1,4,6,2,5,0,3,5};
        int centuryCodeIndex = Math.floorDiv((this.year - 1700), 100);
        int[] centuryCode = {4,2,0,6,4,2,0};
        int leapYearCode = (isLeapYear(this.year) && this.month  <= 1) ? -1 : 0;
        return (yearCode + monthCode[this.month-1] + centuryCode[centuryCodeIndex] +this.day + leapYearCode) % 7;
    }   

    public String getCurrentWeekDay() {
        return WEEKDAYNAMES[this.getCurrentDayNumber()];
    };

    public Date getDateInAmountOfDays(int days) {
        int newDay = days + this.day;
        int newMonth = this.month;
        int newYear = this.year;
        
        int[] yearMonths = Date.getYearDaysInMonths(newYear);
        while(newDay > yearMonths[newMonth-1]) {
            // loop through months until date is reached
            newDay-=yearMonths[newMonth-1];
            newMonth+=1;
            if(newMonth > 12) {
                newMonth = 1;
                yearMonths = Date.getYearDaysInMonths(++newYear);
            }
        }
        return new Date(newDay, newMonth, newYear);
    }
    
    public String getWeekDayInAmountOfDays(int days) {
        return WEEKDAYNAMES[this.getDateInAmountOfDays(days).getCurrentDayNumber()];
    };

    // simple helper function
    public static Date currentDate() {
        return new Date (1, 1, 2024);
    }

    @Override
    public boolean equals(Object object)
    {
        if (object != null && object instanceof Date)
        {
            Date otherDate = (Date) object;
            return this.getMonth() == otherDate.getMonth() 
                && this.getYear() == otherDate.getYear()
                && this.getDay() == otherDate.getDay();
        }
        return false;
    }

    public static String getZeroPadding(int number) {
        String numberToString = String.valueOf(number);
        if (number < 10 ) {
            numberToString = '0'+ numberToString;
        }

        return numberToString;
    }

    @Override
    public String toString() {
        return String.format("%s %s.%s.%d", this.getCurrentWeekDay(), Date.getZeroPadding(this.day), Date.getZeroPadding(this.month), this.year);
    }
}
