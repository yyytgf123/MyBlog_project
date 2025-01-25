/* -- vpc --*/
variable "eks_private_subnet_ids" {
  type = list(string)
}

/* -- ec2 --*/
variable "mb_ec2_launch_template" {
  type = string
}

/* -- eks -- */
variable "mb_eks_cluster" {
  type = string
}

/* -- alb -- */
variable "mb_alb_tg" {
  type = string
}