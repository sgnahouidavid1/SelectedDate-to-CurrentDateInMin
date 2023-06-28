package com.example.dateofbrithcalculator

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.CalendarView
import android.widget.TextView
import android.widget.Toast
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class MainActivity : AppCompatActivity() {
    // making it pravate so it can only be used in the MainActivity and never accessible from another class
    private var tvSelectedDate: TextView? = null // Creating a TextView variable (tvSelectedDate) as nullable so it can be set to null and be used later
    private var tvMinSinceDate: TextView? = null // Creating a TextView variable (tvMinSinceDate) as nullable so it can be set to null and be used later
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // create a button that will display a calendar base the button ID (btnDatePicker)
        val btnDatePicker: Button = findViewById(R.id.btnDatePicker)
        tvSelectedDate = findViewById(R.id.tvSelectedDate) // tvSelectedDate will be found by its ID
        tvMinSinceDate = findViewById((R.id.tvMinSinceDate))
        btnDatePicker.setOnClickListener{ // creating and OnClickedListener that will execute
        // code when the button is clicked
        // the function clickDatePicker() will be executed:
                clickDatePicker()
        }

    }
    private fun clickDatePicker(){

        val myCalendar = Calendar.getInstance() // this code will give us access to the current day, month and year.
        // Getting the year from a Calendar:
        val year = myCalendar.get(Calendar.YEAR)
        //Getting the Month from a Calendar
        val month = myCalendar.get(Calendar.MONTH)
        //Getting the Day from a Calendar
        val day = myCalendar.get(Calendar.DAY_OF_MONTH)
        val dpd = DatePickerDialog(this, // Creating a Calendar to get the year,month,and day
            DatePickerDialog.OnDateSetListener { _, SelectedYear, SelectedMonth, SelectedDayOfMonth ->
                Toast.makeText(
                    this,
                    "Year is $SelectedYear, Month is ${SelectedMonth + 1}, " +
                            "Day-of-Month is $SelectedDayOfMonth", Toast.LENGTH_LONG
                ).show()
                // In Android Studio when creating a Calendar and getting the month with the Calender the month are number
                // starting from zero(january) to eleven(december) so to get an accurate number for the month, month must be added
                //by one {month+1}
                val selectedDate = "${SelectedMonth + 1}/$SelectedDayOfMonth/$SelectedYear"
                tvSelectedDate?.text =
                    selectedDate //when the date is selected from the calendar selectedDate
                // will take the information and pass it to tvSelectedDate?.text (can also take in a null because of  the ? mark)
                // and will assign the information from selectedDate to the textview, so the selected date will be show on the application.

                val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH) // this will allow the program defined a pattern "dd/MM/yyyy" that we want to use for the date
                // Locale.ENGLISH is used to pick the language
                val theDate = sdf.parse(selectedDate) // this will be use to calculate the time that has passed since the date
                // fixing theDate type safety:
                theDate?.let{ // if theDate is not empty run the below code
                    val selectDateInMinutes = theDate.time / 6000 // we use .time to just the time a the date that is located within
                    // (.time use here returns the millisecond of Jan 01 1970 and certain date, and to get the minutes since that date, we must divide the time by 6000 to get the time in minutes from Jan 01 1970 to selected Date.)
                    val currentDate = sdf.parse(sdf.format(System.currentTimeMillis())) // this will give me the time that has passed since Jan 01 1970 and present date  in millisecond
                    currentDate?.let{ // run if current date is not empty.
                        val currentDateInMinutes = currentDate.time / 6000
                        val CalDifferenceInMinutes = currentDateInMinutes - selectDateInMinutes // this will give out the difference between the selected date and the current/present one.
                        tvMinSinceDate?.text = "${CalDifferenceInMinutes.toString()} minutes " // CalDifferenceInMinutes is type long so we use .toString() to convert
                        // CalDifferenceInMinutes from a long type to a string type.
                    }

                }


            },
            year,
            month,
            day
            )
        // .maxDate is used to set a maximum date that the user can not select a date above it.
        dpd.datePicker.maxDate = System.currentTimeMillis() - 864000 // this will make sure the user can not select a date over the current data
        dpd.show()

        // Toast allow the application to display a brief message to the screen.
        Toast.makeText(this,// when the button is pressed a massage will be show (btnDataPicker has been pressed)
        "btnDatePicker has been pressed", Toast.LENGTH_LONG).show()// Toast.LENGTH_LONG will allow
        // the message to be show for a long period of time.
        // .show() allow the code to be shown.
    }
}