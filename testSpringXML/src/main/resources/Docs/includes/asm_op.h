/* ************************************************************************** */
/*                                                                            */
/*                                                        :::      ::::::::   */
/*   asm_op.h                                           :+:      :+:    :+:   */
/*                                                    +:+ +:+         +:+     */
/*   By: bcharity <marvin@student.21-school.ru>     +#+  +:+       +#+        */
/*                                                +#+#+#+#+#+   +#+           */
/*   Created: 2020/07/22 23:22:33 by bcharity          #+#    #+#             */
/*   Updated: 2020/07/22 23:27:07 by bcharity         ###   ########.fr       */
/*                                                                            */
/* ************************************************************************** */

#ifndef ASM_OP_H
# define ASM_OP_H

# include "op.h"

# define TRUE 1
# define FALSE 0

typedef unsigned int		t_4b;

typedef struct s_op			t_op;

struct						s_op
{
	char					*name;
	t_4b					code;
	t_4b					args_num;
	t_4b					args_types_code;
	t_4b					args_types[3];
	t_4b					t_dir_size;
};

t_op			g_op_tab[16] = {
	{
		.name = "live",
		.code = 0x01,
		.args_num = 1,
		.args_types_code = FALSE,
		.args_types = {T_DIR, 0, 0},
		.t_dir_size = 4,
	},
	{
		.name = "ld",
		.code = 0x02,
		.args_num = 2,
		.args_types_code = TRUE,
		.args_types = {T_DIR | T_IND, T_REG, 0},
		.t_dir_size = 4,
	},
	{
		.name = "st",
		.code = 0x03,
		.args_num = 2,
		.args_types_code = TRUE,
		.args_types = {T_REG, T_REG | T_IND, 0},
		.t_dir_size = 4,
	},
	{
		.name = "add",
		.code = 0x04,
		.args_num = 3,
		.args_types_code = TRUE,
		.args_types = {T_REG, T_REG, T_REG},
		.t_dir_size = 4,
	},
	{
		.name = "sub",
		.code = 0x05,
		.args_num = 3,
		.args_types_code = TRUE,
		.args_types = {T_REG, T_REG, T_REG},
		.t_dir_size = 4,
	},
	{
		.code = 0x06,
		.name = "and",
		.args_num = 3,
		.args_types_code = TRUE,
		.args_types = {T_REG | T_DIR | T_IND, T_REG | T_DIR | T_IND, T_REG},
		.t_dir_size = 4,
	},
	{
		.name = "or",
		.code = 0x07,
		.args_num = 3,
		.args_types_code = TRUE,
		.args_types = {T_REG | T_DIR | T_IND, T_REG | T_DIR | T_IND, T_REG},
		.t_dir_size = 4,
	},
	{
		.name = "xor",
		.code = 0x08,
		.args_num = 3,
		.args_types_code = TRUE,
		.args_types = {T_REG | T_DIR | T_IND, T_REG | T_DIR | T_IND, T_REG},
		.t_dir_size = 4,
	},
	{
		.name = "zjmp",
		.code = 0x09,
		.args_num = 1,
		.args_types_code = FALSE,
		.args_types = {T_DIR, 0, 0},
		.t_dir_size = 2,
	},
	{
		.name = "ldi",
		.code = 0x0A,
		.args_num = 3,
		.args_types_code = TRUE,
		.args_types = {T_REG | T_DIR | T_IND, T_REG | T_DIR, T_REG},
		.t_dir_size = 2,
	},
	{
		.name = "sti",
		.code = 0x0B,
		.args_num = 3,
		.args_types_code = TRUE,
		.args_types = {T_REG, T_REG | T_DIR | T_IND, T_REG | T_DIR},
		.t_dir_size = 2,
	},
	{
		.name = "fork",
		.code = 0x0C,
		.args_num = 1,
		.args_types_code = FALSE,
		.args_types = {T_DIR, 0, 0},
		.t_dir_size = 2,
	},
	{
		.name = "lld",
		.code = 0x0D,
		.args_num = 2,
		.args_types_code = TRUE,
		.args_types = {T_DIR | T_IND, T_REG, 0},
		.t_dir_size = 4,
	},
	{
		.name = "lldi",
		.code = 0x0E,
		.args_num = 3,
		.args_types_code = TRUE,
		.args_types = {T_REG | T_DIR | T_IND, T_REG | T_DIR, T_REG},
		.t_dir_size = 2,
	},
	{
		.name = "lfork",
		.code = 0x0F,
		.args_num = 1,
		.args_types_code = FALSE,
		.args_types = {T_DIR, 0, 0},
		.t_dir_size = 2,
	},
	{
		.name = "aff",
		.code = 0x10,
		.args_num = 1,
		.args_types_code = TRUE,
		.args_types = {T_REG, 0, 0},
		.t_dir_size = 4,
	}
};

#endif
