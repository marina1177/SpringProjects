/* ************************************************************************** */
/*                                                                            */
/*                                                        :::      ::::::::   */
/*   vm.h                                               :+:      :+:    :+:   */
/*                                                    +:+ +:+         +:+     */
/*   By: clala <clala@student.42.fr>                +#+  +:+       +#+        */
/*                                                +#+#+#+#+#+   +#+           */
/*   Created: 2020/08/01 12:09:21 by clala             #+#    #+#             */
/*   Updated: 2020/08/01 12:55:28 by clala            ###   ########.fr       */
/*                                                                            */
/* ************************************************************************** */

#ifndef VM_H
# define VM_H

# include <fcntl.h>
# include <string.h>
# include <stdlib.h>
# include <stdint.h>
# include "op.h"
# include "vs_consts.h"
# include "../libft_clala/includes/ft_printf.h"
# include "../libft_clala/includes/libft.h"
# include "vm_error.h"
# include <stdio.h>

/*
** ncurses consts, deprecated
*/
# define RUNNING 			713
# define CYCLE_PER_SEC		1225
# define CYCLE_COORD		1993
# define PROCESSES			2505
# define PL_ONE_LIVE		3529

/*
** op codes
*/
# define LIVE_CODE	0x01
# define LD_CODE 	0x02
# define ST_CODE	0x03
# define ADD_CODE	0x04
# define SUB_CODE	0x05
# define AND_CODE	0x06
# define OR_CODE	0x07
# define XOR_CODE	0x08
# define ZJMP_CODE	0x09
# define LDI_CODE	0x0a
# define STI_CODE	0x0b
# define FORK_CODE	0x0c
# define LLD_CODE	0x0d
# define LLDI_CODE	0x0e
# define LFORK_CODE	0x0f
# define AFF_CODE	0x10

/*
** op consts
*/
# define LIVE_CYCLE_CD	10
# define LD_CYCLE_CD	5
# define ST_CYCLE_CD	5
# define ADD_CYCLE_CD	10
# define SUB_CYCLE_CD	10
# define AND_CYCLE_CD	6
# define OR_CYCLE_CD	6
# define XOR_CYCLE_CD	6
# define ZJMP_CYCLE_CD	20
# define LDI_CYCLE_CD	25
# define STI_CYCLE_CD	25
# define FORK_CYCLE_CD	800
# define LLD_CYCLE_CD	10
# define LLDI_CYCLE_CD	50
# define LFORK_CYCLE_CD	1000
# define AFF_CYCLE_CD	2
# define CYCLES_BEFORE_DUMP 1000
# define VERB_L0 0
# define VERB_L1 1
# define VERB_L2 2
# define VERB_L3 4
# define VERB_L4 8
# define VERB_L5 16

/*
** free consts
*/
# define ALLOCATED_PLAYERS 1
# define ALLOCATED_DATA 2
# define ALLOCATED_CARR 4
# define ALLOCATED_MODS 8
# define ALLOCATED_VS 16
# define ALLOCATED_CELLS 32

/*
** op.h standart values
*/
# define STD_IND_SIZE 2
# define STD_REG_SIZE 4
# define STD_DIR_SIZE 4
# define STD_REG_CODE 1
# define STD_DIR_CODE 2
# define STD_IND_CODE 3
# define STD_MAX_ARGS_NUMBER 4
# define STD_MAX_PLAYERS 4
# define STD_MEM_SIZE_MAX 0xffff
# define STD_MEM_SIZE_MIN 1024
# define STD_REG_NUMBER 16
# define STD_CYCLE_DELTA 50
# define STD_NBR_LIVE 21
# define STD_MAX_CHECKS 10
# define STD_PROG_NAME_LENGTH 128
# define STD_COMMENT_LENGTH 2048

typedef struct			s_op
{
	char				*name;
	int					args_num;
	unsigned int		arr[3];
	int					index;
	int					loop;
	char				*description;
	int					arg_type;
	int					t_dir_size;
}						t_op;

extern t_op				g_op_tab[17];

typedef	struct			s_player
{
	int					num;
	char				*name;
	char				*comment;
	int					code_size;
	char				*code;
	struct s_player		*next;
	int					last_live;
	int					is_alive;
	int					start_code;
	int					lives_in_period;
}						t_player;

typedef struct			s_players
{
	int					qty;
	struct s_player		*first_player;
	struct s_player		*last_player;
	int					last_alive_num;
	int					lives_num;
}						t_players;

typedef struct			s_mods
{
	int					dump_cycle;
	int					dump_size;
	int					verb_lvl;
	int					show_cycle;
	int					show_print_mode;
	int					aff;
	int					vs;
	int					pc;
}						t_mods;

typedef struct			s_vm_info
{
	uint8_t				arena[MEM_SIZE];
	int					cycles;
	int					cycles_to_die;
	int					cycles_after_check;
	size_t				checks_num;
	int					checks_counter;
	int					lives_counter;
}						t_vm_info;

/*
** vis structures
*/
typedef struct			s_content
{
	char				**words;
	struct s_content	*next;

}						t_content;

typedef struct			s_json_item
{
	int					value;
	char				title[128];
	char				keys[9][128];
}						t_json_item;

typedef struct			s_vs
{
	uint8_t				error_code;
	uint8_t				state_refresh;
	uint8_t				players_refresh;
	uint8_t				carriages_refresh;
	uint8_t				cells_refresh;
	int					fd;
	struct s_json_item	vs_const[7];
	struct s_json_item	vs_change[9];
}						t_vs;

typedef struct			s_cells
{
	int					players_id;
	int					num_addr;
	int					cells_address[MEM_SIZE];
	struct s_cells		*last;
	struct s_cells		*next;
}						t_cells;

/*
** core strusctures
*/
typedef struct			s_carriage
{
	struct s_carriage	*next;
	struct s_carriage	*prev;
	int					num;
	int					carry;
	int					regs[REG_NUMBER + 1];
	int					pos;
	int					op_code;
	int					cycles_countdown;
	int					last_cycle_alive;
	int					init_player;
}						t_carriage;

typedef struct			s_carriages
{
	struct s_carriage	*head;
	struct s_carriage	*tail;
	int					qty;
	int					nums;
}						t_carriages;

typedef struct			s_vm
{
	struct s_vm_info	*data;
	struct s_players	*players;
	struct s_carriages	*carr;
	struct s_vs			*vs;
	struct s_cells		*cells;
	struct s_mods		*mods;
	t_op				op_tab[17];
	void				(*exec[17])();
	int					allocated;
}						t_vm;

typedef	struct			s_bit
{
	unsigned			forth : 2;
	unsigned			third : 2;
	unsigned			second : 2;
	unsigned			first : 2;
}						t_bit;

typedef	union			u_arg_types
{
	unsigned char		types;
	t_bit				bit;
}						t_arg_types;

/*
** vs_refresh.c
*/
int						vs_state_refresh(t_vm *vm);
int						vs_players_refresh(t_vm *vm);
int						vs_carriages_refresh(t_vm *vm);
int						vs_cells_refresh(t_vm *vm);
int						vs_reset_refresh(t_vm *vm);

/*
** vs_add_cells.c
*/
t_cells					*new_cells(t_cells **head, int	id, t_vm *vm);
t_cells					*find_id(t_cells **head, int id, t_vm *vm);
void					push_cells(t_vm *vm, int player_id, int	cell_number);

/*
** vs_package.c
*/
void					put_atom_const(t_vm *vm);
void					put_array_const(t_vm *vm);
void					put_file(t_vm *vm, int type);
void					print_vs(t_vm	*vm, int type);

/*
** vs_put_change.c
*/
void					put_cells(t_vm *vm);
void					put_carriages(t_vm *vm);
void					put_players(t_vm *vm);
int						put_state(t_vm *vm);

/*
** vs_utilites.c
*/
int						count_dig(size_t val);
int						vs_putstr_fd(int fd, const char *s);
int						vs_strcpy(char *dest, const char *src);
int						vs_itoa(int n, char *s);
void					vs_print_error(t_vm *vm, char *error_msg);

/*
** vs_itoa_fd.c
*/
int						vs_itoa_fd(int fd, int n);

/*
** core functions
*/
void					init(t_vm *data, int quantity);
void					init_arena(t_vm *data, int quantity);
void					read_data(char *filename, t_player *player);
void					ft_exit(char *line);
void					clean_data(t_vm *data);
void					test(int op, unsigned char *arena);
void					print_byte(unsigned char c);
void					print_memory(const void *addr, size_t size);
int						check_operation(unsigned char *arena,
							t_carriage *carriage, unsigned char *arguments);
int						valid_operation_code(t_carriage *carriage);
void					make_operation(t_vm *vm,
							t_carriage *carriage, unsigned char *arguments);
void					change_position(int *position, int change);
int						get_num_from_char(unsigned char *arena,
							int position, int size);
int						get_negative_number(void *argument, int size);
int						get_arg_size(int op, unsigned char arg);
void					write_reg(unsigned char *arena,
							int reg, int position, int change);
int						get_arg_value(unsigned char *arena,
							t_carriage *car, int *pos, char arg_type);
int						get_reg_value(unsigned char *arena, int *pos);
void					do_live(t_carriage *carriage, t_vm *vm);
void					do_ld(t_carriage *carriage, t_vm *vm,
							unsigned char *arguments);
void					do_st(t_carriage *carriage, t_vm *vm,
							unsigned char *arguments);
void					do_add(t_carriage *carriage, t_vm *vm,
							unsigned char *arguments);
void					do_sub(t_carriage *carriage, t_vm *vm,
							unsigned char *arguments);
void					do_and(t_carriage *carriage, t_vm *vm,
							unsigned char *arguments);
void					do_or(t_carriage *carriage, t_vm *vm,
							unsigned char *arguments);
void					do_xor(t_carriage *carriage, t_vm *vm,
							unsigned char *arguments);
void					do_zjmp(t_carriage *carriage, t_vm *vm);
void					do_ldi(t_carriage *carriage, t_vm *vm,
							unsigned char *arguments);
void					do_sti(t_carriage *carriage, t_vm *vm,
							unsigned char *arguments);
void					do_fork(t_carriage *carriage, t_vm *vm);
void					do_lld(t_carriage *carriage, t_vm *vm,
							unsigned char *arguments);
void					do_lldi(t_carriage *carriage, t_vm *vm,
							unsigned char *arguments);
void					do_lfork(t_carriage *carriage, t_vm *vm);
void					do_aff(t_carriage *carriage, t_vm *vm,
							unsigned char *arguments);

/*
**	op_code fucntions
*/
void					handle_carriages(t_vm *vm);
void					game(t_player *players[], t_vm *vm);
void					display_memory(char *arena);
int						exec_op(t_carriage *carriage, t_vm *vm);

unsigned char			*get_args(char arg_code, unsigned char arr[4]);
int						check_args(int *step, unsigned char *args,
							t_carriage *curr, t_vm *vm);
int						get_int(int *pos, t_carriage *curr,
							t_vm *vm, int size);
void					set_int(int *pos, int num, t_vm *vm);
void					check_position(int *pos, t_vm *vm);
int						get_one_arg(int *temp, unsigned char code,
							t_carriage *curr, t_vm *vm);
int						get_one_arg_no_md(int *temp, unsigned char code,
							t_carriage *curr, t_vm *vm);
void					increase_position(int *pos, int delta);
void					test_display(char*arena);
void					delete_old_carriage(t_vm *vm);
void					display_arena(t_vm *vm);
int						norm_pos(int pos);

/*
** end op_code functions
*/

/*
** t_players functions
*/
t_players				*t_players_create(t_vm *vm);
t_player				*t_players_add(t_players *players, int number,
							t_vm *vm);
void					handle_players(t_vm *vm, t_players *players);
t_player				*get_player_by_number(t_players *players, int num);
int						t_players_get_next_number(t_players *players);

/*
** t_players functions
*/
t_player				*t_player_create(int number, t_vm *vm);
void					t_players_reset_lives_in_period(t_players *players);
void					t_players_check_is_alive(t_vm *vm, t_players *players);

/*
** errors handling
*/
int						handle_error(char *s);
int						handle_error_vm(char *error_message, t_vm *vm);
int						handle_error_str_arg(char *error_message,
							char *arg, t_vm *vm);
int						handle_error_int_arg(char *error_message, int arg,
							t_vm *vm);
int						print_usage(void);
void					t_vm_free(t_vm *vm);
int						error_op_h(char *def_name, int prop_val);
int						error_range_op_h(char *def_name, int min_val,
							int max_val);
/*
** t_carriage and t_carriages
*/
t_carriage				*t_carriage_new(t_carriages *carr, int pos);
t_carriage				*t_carriages_insert_after(t_carriages *list,
							t_carriage *node, t_carriage *new);
t_carriage				*t_carriages_insert_before(t_carriages *list,
							t_carriage *node, t_carriage *new);
t_carriage				*t_carriages_push(t_carriages *list, t_carriage *new);
t_carriages				*t_carriages_create(t_vm *vm);
t_carriage				*t_carriages_pop(t_carriages *list, t_carriage *node);
t_carriages				*t_carriages_remove_node(t_carriages *list,
							t_carriage *node);
int						*get_nums(t_carriages *carrs);
t_carriage				*t_carriage_copy(t_carriages *carr, t_carriage *src);

/*
**	Validation
*/
void					parse_args(t_vm *vm, int ac, char **av);
int						parse_player(t_vm *vm, char *arg, t_player *player);
int						is_integer(char *s);
int						is_valid_op_h(void);

/*
** vm, vsm mods create
*/
t_vm					*t_vm_create(void);
t_vs					*t_vs_create(t_vm *vm);
t_mods					*t_mods_create(t_vm *vm);
t_vm_info				*t_vm_info_create(t_vm *vm);

/*
** print functions, misc
*/
void					print_t_player(t_player *player);
void					print_t_players(t_players *players);
void					print_t_carriage(t_carriage *carriage);
void					print_t_carriages(t_vm *vm);
void					print_introduction(t_players *players);
int						print_is_alive(int num, char *player_name);
int						print_dump(t_vm *vm, int dump_size);
void					print_final_result(t_vm *vm);
void					print_sti(unsigned char *arguments, int temp,
							int *values, int reg);
int						print_st(t_carriage *carriage, int reg,
							int *values, int temp_val);
void					print_live(t_vm *vm, t_carriage *carriage, int num);
void					print_lld(t_carriage *carriage,
							unsigned char *arguments, int *values);
void					print_ldi(t_carriage *carriage,
							unsigned char *arguments, int *values,
							int temp_pos);
void					print_lldi(t_carriage *carriage,
							unsigned char *arguments,
							int *values, int temp_pos);
void					print_bitwise_op(t_vm *vm,
							t_carriage *carriage, int *values);
void					print_move(t_vm *vm, t_carriage *carriage,
							int temp_pos);
int						print_vsconst(t_vm	*vm, int type);
void					vs_push_cells(t_vm *vm,
							int cell_number, t_carriage *carriage);
#endif
