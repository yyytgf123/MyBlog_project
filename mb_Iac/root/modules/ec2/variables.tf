/* -- vpc -- */
variable "public_subnet_id" {
  type = string
}

variable "private_subnet_id" {
  type = string
}

/* -- sg -- */
variable "bastion_security_group_id" {
  type = string
}

variable "private_security_group_id" {
  type = string
}

/* -- iam -- */
variable "eks_workernode_role" {
  type = string
}

variable "eks_cluster_name" {
  type = string
}