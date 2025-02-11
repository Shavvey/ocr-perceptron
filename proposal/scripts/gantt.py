import plotly.express as px
import pandas as pd
from enum import Enum

"""
@Author: Cole Johnson
@Desc: Generate a Gantt chart for the series of tasks
that need to be completed for the final project.
Code is based on: https://plotly.com/python/gantt/
"""

TASKS: pd.DataFrame  # define a series of task to be completed for the project


class AssignedTo(Enum):
    Group = 1  # assigned to everyone
    # assigned to specified people
    Cole = 2
    Lauren = 3
    John = 4
    Colin = 5


TASKS = pd.DataFrame(
    [
        dict(
            Task="UML Use-Case and Class Diagram",
            Start="2025-2-12",
            Finish="2025-2-19",
            Assigned=AssignedTo.Group,
        ),
        dict(
            Task="Create basic components of NN",
            Start="2025-2-19",
            Finish="2025-2-28",
            Assigned=AssignedTo.Cole,
        ),
        dict(
            Task="Set up virtual machine environment for testing",
            Start="2025-2-17",
            Finish="2025-2-27",
            Assigned=AssignedTo.John,
        ),
    ]
)


def main(tasks: pd.DataFrame) -> None:
    """Use `TASKS` dataframe to render
    out a Gantt chart that describes all the tasks
    to be completed"""

    fig = px.timeline(
        tasks, x_start="Start", x_end="Finish", y="Task", color="Assigned"
    )
    fig.update_yaxes(
        autorange="reversed"
    )  # otherwise tasks are listed from the bottom up
    fig.show()


if __name__ == "__main__":
    main(TASKS)
