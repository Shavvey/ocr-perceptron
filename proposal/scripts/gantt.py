import plotly.express as px
import pandas as pd

"""
@Author: Cole Johnson
@Desc: Generate a Gantt chart for the series of tasks
that need to be completed for the final project.
Code is based on: https://plotly.com/python/gantt/
"""

TASKS: pd.DataFrame  # define a series of task to be completed for the project

TASKS = pd.DataFrame(
    [
        dict(Task="Job A", Start="2009-01-01", Finish="2009-02-28"),
        dict(Task="Job B", Start="2009-03-05", Finish="2009-04-15"),
        dict(Task="Job C", Start="2009-02-20", Finish="2009-05-30"),
    ]
)


def main(tasks: pd.DataFrame) -> None:
    """Use `TASKS` dataframe to render
    out a Gantt chart that describes all the tasks
    to be completed"""

    fig = px.timeline(tasks, x_start="Start", x_end="Finish", y="Task")
    fig.update_yaxes(
        autorange="reversed"
    )  # otherwise tasks are listed from the bottom up
    fig.show()


if __name__ == "__main__":
    main(TASKS)
