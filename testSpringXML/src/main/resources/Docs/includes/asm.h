/* ************************************************************************** */
/*                                                                            */
/*                                                        :::      ::::::::   */
/*   asm.h                                              :+:      :+:    :+:   */
/*                                                    +:+ +:+         +:+     */
/*   By: arz <arz@student.42.fr>                    +#+  +:+       +#+        */
/*                                                +#+#+#+#+#+   +#+           */
/*   Created: 2020/07/22 22:48:05 by bcharity          #+#    #+#             */
/*   Updated: 2020/08/03 22:24:08 by arz              ###   ########.fr       */
/*                                                                            */
/* ************************************************************************** */

#ifndef ASM_H
# define ASM_H

# include "../libft_clala/includes/libft.h"
# include "../libft_clala/includes/ft_printf.h"
# include "op.h"
# include "asm_error.h"
# include "dasm.h"
# include <errno.h>
# include <sys/types.h>
# include <sys/stat.h>
# include <fcntl.h>
# include <unistd.h>
# include <stdlib.h>
# include <string.h>
# include <stdio.h>

# define EOC				"\033[0m"
# define RED				"\033[1;31m"
# define YELLOW				"\033[1;33m"
# define WHITE				"\033[1;37m"
# define BLACK				"\033[0;30m"
# define GREEN				"\033[0;32m"
# define BLUE				"\033[0;34m"
# define PURPUL				"\033[0;35m"
# define CYAN				"\033[0;36m"
# define GRAY				"\033[1;30m"
# define TRUE				1
# define FALSE				0
# define REGISTER			(t_2b)0b10000000000000
# define DIRECT				(t_2b)0b01000000000000
# define DIRECT_LABEL		(t_2b)0b00100000000000
# define INDIRECT			(t_2b)0b00010000000000
# define INDIRECT_LABEL		(t_2b)0b00001000000000
# define COMMAND			(t_2b)0b00000100000000
# define STRING				(t_2b)0b00000010000000
# define LABEL				(t_2b)0b00000001000000
# define INSTRUCTION		(t_2b)0b00000000100000
# define SEPARATOR			(t_2b)0b00000000010000
# define NEW_LINE			(t_2b)0b00000000001000
# define END				(t_2b)0b00000000000100
# define NAME				(t_2b)0b00000000000010
# define COMMENT			(t_2b)0b00000000000001
# define F_OPRINT			(t_1b)0b00000010
# define F_DISASM			(t_1b)0b00000001

typedef unsigned char		t_1b;
typedef unsigned short		t_2b;
typedef unsigned int		t_4b;
typedef unsigned long		t_8b;
typedef struct s_opargs		t_opargs;
typedef struct s_lbl_lst	t_lbl_lst;
typedef struct s_token		t_token;
typedef struct s_mdata		t_mdata;

struct						s_op
{
	char					*name;
	int						args_num;
	unsigned int			args_types[3];
	int						code;
	int						loop;
	char					*description;
	int						args_types_code;
	int						t_dir_size;
};

typedef struct s_op			t_op;
extern t_op					g_op_tab[16];

struct						s_opargs
{
	int						x;
	int						y;
	char					*arg;
	int32_t					argsize;
	t_2b					argtype;
	int						value;
};

struct						s_token
{
	int						x;
	int						y;
	t_2b					new_line;
	t_op					*op;
	t_opargs				*args[3];
	int						cnt_args;
	int32_t					offset;
	int32_t					num_byte_op;
	t_token					*next;
	t_token					*prev;
};

struct						s_mdata
{
	int						x;
	int						y;
	char					*line;
	t_op					*g_op_tab;
	char					*filename;
	t_header				*head;
	int						fd_s;
	t_4b					name_f;
	t_4b					comm_f;
	int64_t					exec_bytes;
	t_2b					exist_nl;
};

struct						s_lbl_lst
{
	char					*label;
	int32_t					offset;
	t_2b					new_line;
	t_lbl_lst				*next;
};

/*
** print_bits.c
*/
char						*get_color(size_t num_byte);
void						ft_print_hex(unsigned char c, char *color);
void						ft_print_char(unsigned char c, char *color);
void						bits_to_str(size_t size, void *ptr, char space);
void						print_bits(size_t size, void *ptr, char space);

/*
** print_memory.c
*/
void						print_memory(const void *addr, size_t size);

/*
** error.c
*/
void						put_error(char *err, int type);
void						print_error(char *message);
void						error_line(char *event, int start_line);
void						error_event(char *event);
void						error(void);

/*
** my_atoi.c
*/
int							my_atoi(const char *str);

/*
** supfun_2.c
*/
void						int_to_hex(int32_t dec, int dir_size,
							u_int32_t *place);
int							ft_findchar(char *str, int c);
int							ft_strmerge(char **dest, char **srcs);

/*
** supfun.c
*/
int32_t						ft_atoi_cor(const char *str, u_int8_t size);
int							is_lblchar(char c);
void						skip_space(char *line);
void						skip_comment(char *s);
void						check_new_line(char *line, int f);

/*
** free_data.c
*/
void						freesplit(t_token *tmp);
void						free_token(void);
void						free_label(void);
void						free_data(void);

/*
** write_to_file.c
*/
void						write_to_file(void);

/*
** translate.c
*/
int32_t						process_label(t_token **tkn, char *label);
void						print_args(t_token **tkn, u_int32_t *cursor);
void						print_args_types_code(t_token *tkn,
							u_int32_t *cursor);
void						print_champion_info(void);
void						translate(void);

/*
** is_type.c
*/
int							is_reg(char *line, int len);
int							is_direct(char *line, int len);
int							is_dir_label(char *line, int len);
int							is_indirect(char *line, int len);
int							is_ind_label(char *line, int len);

/*
** check_type_arg.c
*/
int							check_reg(char *line, int size, u_int16_t i);
int							check_dir(char *line, int size, u_int16_t i);
int							check_ind(char *line, int size, u_int16_t i);

/*
** parse_args_type.c
*/
void						parse_args_type(u_int16_t i, char *line);

/*
** parse_args.c
*/
t_opargs					*parse_parameter(char *line);
void						parse_args(char *line);

/*
** check_op.c
*/
int							search_op(char *line);
t_token						*new_token(void);
void						add_token(int indx_op);
void						check_op(char *line);

/*
** add_label.c
*/
void						add_lbl(char *s, size_t size);

/*
** check_label.c
*/
void						del_dubl(t_lbl_lst *ptr2);
void						check_empty_file(void);
void						check_dup_label(void);
void						check_new_line(char *line, int f);
void						add_lbl(char *s, size_t size);
void						check_label(char *line);

/*
** add_header.c
*/
void						valid_headlen(t_2b type_h, int len);
void						put_header(char *str, int len, t_2b type_h);
void						process_header(char **line, t_2b type_h);
t_2b						exist_header(char **line);
void						add_header(char **line);

/*
** get_line.c
*/
int							get_line(const int fd, char **row);

/*
** parse_file.c
*/
void						parse_str(char **line);
void						parse_file();

/*
** main.c
*/
void						valid_filename(char *fname);
void						data_init(void);
void						compilation(void);
void						read_file(char *filename);
t_1b						put_flg(char *av);
void						usage(void);

t_mdata						*g_mdata;
t_token						*g_tkn_first;
t_token						*g_tkn_last;
t_lbl_lst					*g_label_first;
t_lbl_lst					*g_label_last;
char						*g_mbuf;
t_1b						g_flg;

#endif
