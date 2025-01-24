variable "mb_ec2_launch_template" {
  type = string
}

variable "mb_eks_cluster" {
  type = string
}

variable "eks_private_subnet_ids" {
  type = list(string)
}

variable "mb_alb_tg" {
  type = string
}