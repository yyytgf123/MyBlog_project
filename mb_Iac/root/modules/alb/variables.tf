/* -- vpc -- */
variable "vpc_id" {
  type = string
}

variable "public_subnet_ids" {
  type = list(string)
}

/* -- sg --*/
variable "alb_security_group_id" {
  type = string
}
