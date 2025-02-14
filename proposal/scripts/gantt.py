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
            Task="Draft use-case and class diagrams",
            Start="2025-2-13",
            Finish="2025-2-23",
            Assigned="Group",
        ),
        dict(
            Task="Make NN components from class diagram",
            Start="2025-2-23",
            Finish="2025-3-5",
            Assigned="Cole"
        ),
        dict(
            Task="Set up virtual machine environment",
            Start="2025-2-13",
            Finish="2025-2-20",
            Assigned="John"
        ),
        dict(
            Task="Make GUI framework",
            Start="2025-2-13",
            Finish="2025-3-10",
            Assigned="Lauren"
        ),
        dict(
            Task="Make helper library for image processing",
            Start="2025-2-13",
            Finish="2025-3-10",
            Assigned="Group",
        ),
        dict(
            Task="Collect OCR database",
            Start="2025-2-13",
            Finish="2025-2-23",
            Assigned="John, Colin",
        ),
        dict(
            Task="Develop performance metrics to train NNs",
            Start="2025-3-3",
            Finish="2025-3-18",
            Assigned="Cole, John",
        ),
        dict(
            Task="Develop performance metrics to train NNs",
            Start="2025-3-5",
            Finish="2025-4-10",
            Assigned="Cole, John",
        ),
        dict(
            Task="Implement supervised learner",
            Start="2025-3-3",
            Finish="2025-3-18",
            Assigned="Group",
        ),
        dict(
            Task="Make GUI for Drawing Characters",
            Start="2025-3-10",
            Finish="2025-4-21",
            Assigned="Lauren"
        ),
        dict(
            Task="Make GUI for Viewing NNs",
            Start="2025-3-10",
            Finish="2025-4-21",
            Assigned="Lauren, Cole"
        ),
        dict(
            Task="Make web-based version of system",
            Start="2025-4-21",
            Finish="2025-4-28",
            Assigned="John"
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
