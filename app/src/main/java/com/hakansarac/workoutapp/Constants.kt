package com.hakansarac.workoutapp

class Constants {
    companion object{
        /**
         * List of exercises
         */
        fun defaultExercisesList():ArrayList<Exercise>{
            val exercisesList = ArrayList<Exercise>()
            val jumpingJacks = Exercise(
                1,
                "Jumping Jacks",
                R.drawable.ic_jumping_jacks,
                false,
                false
            )
            exercisesList.add(jumpingJacks)

            val wallSit = Exercise(
                2,
                "Wall Sit",
                R.drawable.ic_wall_sit,
                false,
                false
            )
            exercisesList.add(wallSit)

            val pushUp = Exercise(
                3,
                "Push Up",
                R.drawable.ic_push_up,
                false,
                false
            )
            exercisesList.add(pushUp)

            val abdominalCrunch = Exercise(
                4,
                "Abdominal Crunch",
                R.drawable.ic_abdominal_crunch,
                false,
                false
            )
            exercisesList.add(abdominalCrunch)

            val stepUpOnChair = Exercise(
                5,
                "Step-Up onto Chair",
                R.drawable.ic_step_up_onto_chair,
                false,
                false
                )
            exercisesList.add(stepUpOnChair)

            val squat = Exercise(
                6,
                "Squat",
                R.drawable.ic_squat,
                false,
                false
            )
            exercisesList.add(squat)

            val tricepsDipOnChair = Exercise(
                7,
                "Triceps Dip On Chair",
                R.drawable.ic_triceps_dip_on_chair,
                false,
                false
            )
            exercisesList.add(tricepsDipOnChair)

            val plank = Exercise(
                8,
                "Plank",
                R.drawable.ic_plank,
                false,
                false
            )
            exercisesList.add(plank)

            val highKneesRunningInPlace = Exercise(
                9, "High Knees Running In Place",
                R.drawable.ic_high_knees_running_in_place,
                false,
                false
            )
            exercisesList.add(highKneesRunningInPlace)

            val lunges = Exercise(
                10,
                "Lunges",
                R.drawable.ic_lunge,
                false,
                false
            )
            exercisesList.add(lunges)

            val pushUpAndRotation = Exercise(
                11,
                "Push up and Rotation",
                R.drawable.ic_push_up_and_rotation,
                false,
                false
            )
            exercisesList.add(pushUpAndRotation)

            val sidePlank = Exercise(
                12,
                "Side Plank",
                R.drawable.ic_side_plank,
                false,
                false
            )
            exercisesList.add(sidePlank)


            return exercisesList
        }

    }
}