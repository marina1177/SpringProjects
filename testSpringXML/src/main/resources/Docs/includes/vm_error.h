/* ************************************************************************** */
/*                                                                            */
/*                                                        :::      ::::::::   */
/*   vm_error.h                                         :+:      :+:    :+:   */
/*                                                    +:+ +:+         +:+     */
/*   By: clala <clala@student.42.fr>                +#+  +:+       +#+        */
/*                                                +#+#+#+#+#+   +#+           */
/*   Created: 2020/08/01 12:31:41 by clala             #+#    #+#             */
/*   Updated: 2020/08/01 12:36:44 by clala            ###   ########.fr       */
/*                                                                            */
/* ************************************************************************** */

#ifndef VM_ERROR_H
# define VM_ERROR_H

# define ERROR_WORD "Error:"

/*
** Flag errors
*/
# define ERR_NOINT_N "argument after -n (%s) is not integer"
# define ERR_NOINT_D "argument after -d / -dump (%s) is not integer"
# define ERR_NOINT_V "argument after -v (%s) is not integer"
# define ERR_NONUM "-v / -d / -n flags must be followed by integer"
# define ERR_NOPLR "integer after -n flag must be followed by player`s name"
# define ERR_N_FLAG "-n argument (%s) must be in range [1 : MAX_PLAYERS]"
# define ERR_D_FLAG "dump argument (%s) must be in range [0 : INT_MAX]"
# define ERR_V_FLAG "verbosity argument (%s) must be in range [0 : 31]"
# define ERR_N_DUPL "Ambiguous player number: %d"
# define ERR_MAX_PLAYERS "Too many players: number must be in range [1 : %d]"
# define ERR_NO_PLAYERS "No players: must be at least 1 player"
# define ERR_INV_P_NUM "Player`s %s number out of order"

/*
** Argument errors
*/
# define ERR_NOFILE "./corewar recieves wrong number of arguments"
# define ERR_FNAME "File %s doesn`t contain '.cor' extension"
# define ERR_FOPEN "Can\'t open file %s"
# define ERR_READING "Can\'t read file %s"
# define ERR_MAGIC "Invalid magic code in file %s"
# define ERR_ALLOC "Can\'t allocate memory"
# define ERR_CHNAME_LEN "Champion\'s name is too large"
# define ERR_CH_FSIZE "Champion\'s file %s is too short"
# define ERR_NO_NULL "Invalid NULL separator in %s"
# define ERR_GET_TEXT "Can\'t read token"
# define ERR_LABEL_DUB "One label name were used multiple times"

/*
** Champion errors
*/
# define ERR_GET_TEXT "Can\'t read token"
# define ERR_LABEL_DUB "One label name were used multiple times"
# define ERR_NO_CHNAME "Champion\'s name doesn't exist"
# define ERR_CHCOMM_LEN "Champion\'s comment is too large"
# define ERR_NO_CHCOMM "Champion\'s comment doesn't exist"
# define ERR_NAMECOM "Wrong format of champion\'s name/comment"
# define ERR_WRTYPE "There should be .name or .comment"
# define ERR_SYM "Wrong symbol"
# define ERR_TOKEN "It should be sepatated char"
# define ERR_OP "This operator doesn\'t exist"
# define ERR_ARGNO "Wrong number of operator\'s arguments"
# define ERR_ARGTP "Wrong type of operator\'s arguments"
# define ERR_SEP "Lost symbol of separator"
# define ERR_ARG_PLUS "+ symbol in the argument\'s value"
# define ERR_MULT_INST "Multiple instuctions on one line"
# define ERR_LABEL_CH "Wrong characters in the label\'s name"
# define ERR_LABEL_ECH "Wrong ending label character"
# define ERR_LABEL_EX "This label doesn\'t exist"
# define ERR_ZERO_REG "Register r0 (r00) is used, but it doesn\'t exist"
# define ERR_BIGEX "Executable code has size bigger than a limit"

#endif
