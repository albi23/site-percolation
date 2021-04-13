from typing import List


def read_matrix(filNam: str):
    matrix_list = []
    with open(filNam, 'r') as matrixFile:
        for line in matrixFile.readlines():
            line = line.split()
            line = [int(i) for i in line]
            matrix_list.append(line)
    return matrix_list


def colored_matrix_plot(matrix, title: str) -> None:
    import plotly.figure_factory as ff
    tmp = []
    for i in range(len(matrix) - 1, -1, -1):
        tmp.append(matrix[i])
    fig = ff.create_annotated_heatmap(tmp, colorscale='Greys', showscale=False)
    fig.update_xaxes(showline=True, linewidth=1, linecolor='black', mirror=True)
    fig.update_yaxes(showline=True, linewidth=1, linecolor='black', mirror=True)
    fig.update_layout(width=600, height=600, title=title, margin_t=30, title_x=0.5,
                      legend_title_side='top')
    fig.show()


def read_data_file(file_column=1):
    files_names = ['Ave_L10T10000.txt', 'Ave_L50T10000.txt', 'Ave_L100T10000.txt']

    matrix_list = []
    for file_name in files_names:
        tmp_arr = []
        with open(file_name, 'r') as dataFile:
            for line in dataFile.readlines():
                tmp_arr.append(float(line.split()[file_column]))
        matrix_list.append(tmp_arr)
    return matrix_list


def read_data_file_as_column(files_names: List[str]) -> List[dict]:
    matrix_list = []
    for file_name in files_names:
        dict_values = {'x': [], 'y': []}
        with open(file_name, 'r') as dataFile:
            for line in dataFile.readlines():
                split = line.split()
                dict_values['x'].append(int(split[0]))
                dict_values['y'].append(float(split[1]))
        matrix_list.append(dict_values)
    return matrix_list


def generate_flow_plots():
    import plotly.graph_objects as go

    matrix_data = read_data_file()

    probability_step = [i * 0.001 for i in range(1001)]
    fig = go.Figure()
    fig.add_trace(
        go.Scatter(x=probability_step, y=matrix_data[0], mode='markers', line_color='red', name="L = 10",
                   marker=dict(size=8, color='black', symbol='circle'))
    )
    fig.add_trace(
        go.Scatter(x=probability_step, y=matrix_data[1], mode='markers', line_color='blue', name="L = 50",
                   marker=dict(size=8, color='red', symbol='square'))
    )
    fig.add_trace(
        go.Scatter(x=probability_step, y=matrix_data[2], mode='markers', line_color='black', name="L = 100",
                   marker=dict(size=8, color='blue', symbol='triangle-up'))
    )

    fig.update_layout({'plot_bgcolor': 'rgb(255, 255, 255)',
                       'paper_bgcolor': 'rgb(255, 255, 255)'}, width=600, height=600, showlegend=True,
                      title="Probability of occurrence of spanning cluster", margin_t=30, title_x=0.5)
    fig.update_xaxes(showline=True, linewidth=1, linecolor='black', mirror='allticks', ticks='inside', tickwidth=2,
                     ticklen=10, title='<b>p</b>', title_font_size=18, title_font_color='black')
    fig.update_yaxes(showline=True, linewidth=1, linecolor='black', mirror='allticks', ticks='inside', tickwidth=2,
                     ticklen=10, title='<b>P_flow</b>', title_font_size=18, title_font_color='black', tickangle=-90)
    fig.update_traces(marker_line_color='red', selector=dict(type='scatter'))
    fig.show()


def generate_max_cluster_plots():
    import plotly.graph_objects as go

    matrix_data = read_data_file(2)

    probability_step = [i * 0.001 for i in range(1001)]
    sizes = [10, 50, 100]
    for i in range(3):
        fig = go.Figure()
        fig.add_trace(
            go.Scatter(x=probability_step, y=matrix_data[i], mode='markers', line_color='red', name=f"L = {sizes[i]}",
                       marker=dict(size=6, color='black', symbol='circle'))
        )
        fig.update_layout({'plot_bgcolor': 'rgb(255, 255, 255)',
                           'paper_bgcolor': 'rgb(255, 255, 255)'}, width=600, height=600, showlegend=True,
                          title="Average maximum cluster size", margin_t=30, title_x=0.5)
        fig.update_xaxes(showline=True, linewidth=1, linecolor='black', mirror='allticks', ticks='inside', tickwidth=2,
                         ticklen=10, title='<b>p</b>', title_font_size=18, title_font_color='black')
        fig.update_yaxes(showline=True, linewidth=1, linecolor='black', mirror='allticks', ticks='inside', tickwidth=2,
                         ticklen=10, title='<b>Cluster size</b>', title_font_size=18, title_font_color='black',
                         tickangle=-90)
        fig.update_traces(marker_line_color='red', selector=dict(type='scatter'))
        fig.show()

    fig = go.Figure()
    fig.add_trace(
        go.Scatter(x=probability_step, y=matrix_data[0], mode='markers', line_color='red', name=f"L = 10",
                   marker=dict(size=6, color='red', symbol='circle'))
    )
    fig.add_trace(
        go.Scatter(x=probability_step, y=matrix_data[1], mode='markers', line_color='blue', name="L = 50",
                   marker=dict(size=6, color='blue', symbol='square'))
    )
    fig.add_trace(
        go.Scatter(x=probability_step, y=matrix_data[2], mode='markers', line_color='black', name="L = 100",
                   marker=dict(size=6, color='green', symbol='triangle-up'))
    )

    fig.update_layout({'plot_bgcolor': 'rgb(255, 255, 255)'}, width=600, height=600, showlegend=True,
                      # title="Average maximum cluster size",
                      margin_t=30, title_x=0.5)
    fig.update_xaxes(showline=True, linewidth=1, linecolor='black', mirror='allticks', ticks='inside', tickwidth=2,
                     ticklen=10, title='<b>p</b>', title_font_size=18, title_font_color='black', gridcolor='Black')
    fig.update_yaxes(showline=True, linewidth=1, linecolor='black', mirror='allticks', ticks='inside', tickwidth=2,
                     ticklen=10, title='<b>Cluster size</b>', title_font_size=18, title_font_color='black',
                     tickangle=-90, gridcolor='Black')
    fig.update_traces(marker_line_color='red', selector=dict(type='scatter'))
    fig.show()


def distribution_data(l_size: int):
    files_l50 = [
        'Dist_p0.2L50T10000.txt'
        , 'Dist_p0.3L50T10000.txt'
        , 'Dist_p0.4L50T10000.txt'
        , 'Dist_p0.5L50T10000.txt'
        , 'Dist_p0.592746L50T10000.txt'
        , 'Dist_p0.6L50T10000.txt'
        , 'Dist_p0.7L50T10000.txt'
        , 'Dist_p0.8L50T10000.txt'
    ]
    files_l100 = [
        'Dist_p0.2L100T10000.txt'
        , 'Dist_p0.3L100T10000.txt'
        , 'Dist_p0.4L100T10000.txt'
        , 'Dist_p0.5L100T10000.txt'
        , 'Dist_p0.592746L100T10000.txt'
        , 'Dist_p0.6L100T10000.txt'
        , 'Dist_p0.7L100T10000.txt'
        , 'Dist_p0.8L100T10000.txt'
    ]
    files_l10 = [
        'Dist_p0.2L10T10000.txt'
        , 'Dist_p0.3L10T10000.txt'
        , 'Dist_p0.4L10T10000.txt'
        , 'Dist_p0.5L10T10000.txt'
        , 'Dist_p0.592746L10T10000.txt'
        , 'Dist_p0.6L10T10000.txt'
        , 'Dist_p0.7L10T10000.txt'
        , 'Dist_p0.8L10T10000.txt'
        ,
    ]
    if l_size == 50:
        return files_l50
    elif l_size == 100:
        return files_l100
    return files_l10


def generate_distribution_plots():
    import plotly.graph_objects as go

    # files = distribution_data(10)
    # files = distribution_data(50)
    files = distribution_data(100)
    matrix_data = read_data_file_as_column(files)

    fig = go.Figure()
    fig.add_trace(
        go.Scatter(x=matrix_data[0]['x'], y=matrix_data[0]['y'], mode='markers', line_color='red', name=f"p = 0.2",
                   marker=dict(size=6, color='black', symbol='circle'))
    )
    fig.add_trace(
        go.Scatter(x=matrix_data[1]['x'], y=matrix_data[1]['y'], mode='markers', line_color='blue', name=f"p = 0.3",
                   marker=dict(size=6, color='red', symbol='square'))
    )
    fig.add_trace(
        go.Scatter(x=matrix_data[2]['x'], y=matrix_data[2]['y'], mode='markers', line_color='black', name=f"p = 0.4",
                   marker=dict(size=6, color='blue', symbol='triangle-up'))
    )
    fig.add_trace(
        go.Scatter(x=matrix_data[3]['x'], y=matrix_data[3]['y'], mode='markers', line_color='green', name=f"p = 0.5",
                   marker=dict(size=6, color='green', symbol='cross'))
    )

    def set_plot_properties(plot_fig):
        plot_fig.update_layout({'plot_bgcolor': 'rgb(255, 255, 255)',
                                'paper_bgcolor': 'rgb(255, 255, 255)'}, width=600, height=600, showlegend=True,
                               margin_t=30, title_x=0.5)
        plot_fig.update_xaxes(showline=True, linewidth=1, linecolor='black', mirror='allticks', ticks='inside',
                              tickwidth=2,
                              ticklen=10, title='<b>s</b>', title_font_size=18, title_font_color='black')
        plot_fig.update_yaxes(showline=True, linewidth=1, linecolor='black', mirror='allticks', ticks='inside',
                              tickwidth=2,
                              ticklen=10, title='<b>n(s)</b>', title_font_size=18, title_font_color='black', type="log",
                              tickangle=-90)
        plot_fig.update_traces(marker_line_color='red', selector=dict(type='scatter'))

    set_plot_properties(fig)
    fig.show()

    fig2 = go.Figure()
    fig2.add_trace(
        go.Scatter(x=matrix_data[4]['x'], y=matrix_data[4]['y'], mode='markers', line_color='red', name=f"p = 0.592746",
                   marker=dict(size=6, color='black', symbol='circle'))
    )
    set_plot_properties(fig2)
    fig2.show()

    fig3 = go.Figure()
    fig3.add_trace(
        go.Scatter(x=matrix_data[5]['x'], y=matrix_data[5]['y'], mode='markers', line_color='red', name=f"p = 0.6",
                   marker=dict(size=6, color='black', symbol='circle'))
    )
    fig3.add_trace(
        go.Scatter(x=matrix_data[6]['x'], y=matrix_data[6]['y'], mode='markers', line_color='blue', name=f"p = 0.7",
                   marker=dict(size=6, color='red', symbol='square'))
    )
    fig3.add_trace(
        go.Scatter(x=matrix_data[7]['x'], y=matrix_data[7]['y'], mode='markers', line_color='black', name=f"p = 0.8",
                   marker=dict(size=6, color='blue', symbol='triangle-up'))
    )
    set_plot_properties(fig3)
    fig3.show()
    print()


if __name__ == '__main__':
    file_name_array = [('hk_l10_p40.txt', "Hoshen–Kopelman algorithm | L=10 P=0.4"),
                       ('hk_l10_p60.txt', "Hoshen–Kopelman algorithm | L=10 P=0.6"),
                       ('hk_l10_p80.txt', "Hoshen–Kopelman algorithm | L=10 P=0.8"),
                       ('burn_l10_p40.txt', "The Burning Method | L=10 P=0.4"),
                       ('burn_l10_p60.txt', "The Burning Method | L=10 P=0.6"),
                       ('burn_l10_p80.txt', "The Burning Method | L=10 P=0.8")]
    for data in file_name_array:
        colored_matrix_plot(read_matrix(data[0]), data[1])

    # generate_flow_plots()
    # generate_max_cluster_plots()
    # generate_distribution_plots()
