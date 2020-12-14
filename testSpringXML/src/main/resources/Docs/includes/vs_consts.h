/* ************************************************************************** */
/*                                                                            */
/*                                                        :::      ::::::::   */
/*   vs_consts.h                                        :+:      :+:    :+:   */
/*                                                    +:+ +:+         +:+     */
/*   By: bcharity <marvin@student.21-school.ru>     +#+  +:+       +#+        */
/*                                                +#+#+#+#+#+   +#+           */
/*   Created: 2020/07/22 23:36:18 by bcharity          #+#    #+#             */
/*   Updated: 2020/07/22 23:36:19 by bcharity         ###   ########.fr       */
/*                                                                            */
/* ************************************************************************** */

#ifndef VS_CONSTS_H
# define VS_CONSTS_H

/*
** vs_consts
*/

# define M_CONST			"\t\t\"Const\": ["
# define A_MEMSIZE			"\n\t\t\"mem_size\": "
# define A_CYCLEDELTA		",\n\t\t\"cycle_delta\": "
# define A_PLAYERS_NUM		",\n\t\t\"players_number\": "
# define A_MAXCHECKS		",\n\t\t\"max_checks\": "
# define A_C_NBR_LIVE		",\n\t\t\"const_nbr_live\": "
# define A_C_CYCLE_TO_DIE	",\n\t\t\"const_cycle_to_die\": "

# define M_C_PLAYERS		",\n\t\t\"Players\": ["
# define A_C_ID				"\n\t\t\t\"id\": "
# define A_NAME				",\n\t\t\t\"name\": "
# define A_START_CODE		",\n\t\t\t\"start_code\": "
# define A_CODE_SIZE		",\n\t\t\t\"exec_code_size\": "

# define A_END_1			"}\n\t\t\t{"

/*
** vs_change
*/

# define A_CYCLE			"\n\t\"cycle\": "
# define A_STATE_REF		",\n\t\"state_refresh\": "

# define M_STATE			",\n\t\"State\": "
# define A_ERR_CODE			",\n\t\t\"error_code\": "
# define A_TOTAL_PROC		",\n\t\t\"total_process\": "
# define A_CYCLE_TO_DIE		",\n\t\t\"cycle_to_die\": "
# define A_NBR_LIVE			",\n\t\t\"nbr_live\": "

# define A_PLAYERS_REF		"\n\t\"players_refresh\": "
# define M_PLAYERS			",\n\t\"Players\": "
# define A_ID				"\n\t\t\"id\": "
# define A_IS_ALIVE			",\n\t\t\"is_alive\": "
# define A_LAST_LIVE		",\n\t\t\"last_live\": "
# define A_LPP				",\n\t\t\"lives_in_period\": "

# define A_CAR_REF			"\n\t\"carriage_refresh\": "
# define M_CARRIAGE			",\"Carriage\": "
# define A_OP_CODE			",\n\t\t\"op_code\": "
# define A_PLACE			",\n\t\t\"place\": "

# define A_CELLS_REF		"\n\t\"cells_refresh\": "
# define M_CELLS			"\"Cells\": "
# define A_PLAYER_ID		",\n\t\t\"player_id\": "
# define A_NUM_ADDR			",\n\t\t\"num_address\": "

# define A_CELL_ADDR		",\n\t\t\"cell_address\": "

#endif
