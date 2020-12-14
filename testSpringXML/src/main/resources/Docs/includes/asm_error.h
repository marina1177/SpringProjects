/* ************************************************************************** */
/*                                                                            */
/*                                                        :::      ::::::::   */
/*   asm_error.h                                        :+:      :+:    :+:   */
/*                                                    +:+ +:+         +:+     */
/*   By: bcharity <marvin@student.21-school.ru>     +#+  +:+       +#+        */
/*                                                +#+#+#+#+#+   +#+           */
/*   Created: 2020/07/22 23:12:53 by bcharity          #+#    #+#             */
/*   Updated: 2020/07/22 23:14:37 by bcharity         ###   ########.fr       */
/*                                                                            */
/* ************************************************************************** */

#ifndef ASM_ERROR_H
# define ASM_ERROR_H

# define ERR_NOFILE "./asm recieves wrong number of arguments."
# define ERR_FNAME "Given file has wrong name format."
# define ERR_FOPEN "Can\'t open file."
# define ERR_READING "Can\'t read file."
# define ERR_ALLOC "Can\'t allocate memory."
# define ERR_GET_TEXT "Can\'t read token."
# define ERR_LABEL_DUB "One label name were used multiple times."
# define ERR_CHNAME_LEN "Champion\'s name is too large."
# define ERR_NO_CHNAME "Champion\'s name doesn't exist."
# define ERR_CHCOMM_LEN "Champion\'s comment is too large."
# define ERR_NO_CHCOMM "Champion\'s comment doesn't exist."
# define ERR_NAMECOM "Wrong format of champion\'s name/comment."
# define ERR_WRTYPE "There should be .name or .comment."
# define ERR_SYM "Wrong symbol"
# define ERR_TOKEN "It should be sepatated char."
# define ERR_OP "This operator doesn\'t exist."
# define ERR_ARGNO "Wrong number of operator\'s arguments."
# define ERR_ARGTP "Wrong type of operator\'s arguments."
# define ERR_SEP "Lost symbol of separator."
# define ERR_ARG_PLUS "+ symbol in the argument\'s value."
# define ERR_MULT_INST "Multiple instuctions on one line."
# define ERR_LABEL_CH "Wrong characters in the label\'s name."
# define ERR_LABEL_ECH "Wrong ending label character."
# define ERR_LABEL_EX "This label doesn\'t exist."
# define ERR_ZERO_REG "Register r0 (r00) is used, but it doesn\'t exist."
# define ERR_BIGEX "Executable code has size bigger than a limit."
# define ERR_CRFHEX "Can\'t create machine-code file."
# define ERR_WRFHEX "Can\'t write to machine-code file."
# define ERR_STR_STOP "Can\'t find ending of the string."
# define ERR_NLEOF "There should be new line of EOF"
# define FOGOT_NL "Syntax error - unexpected end of input"

#endif
