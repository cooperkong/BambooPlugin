package models.project

data class Projects(val maxResult: Int = 0,
                    val expand: String = "",
                    val size: Int = 0,
                    val startIndex: Int = 0,
                    val project: List<ProjectItem>?)