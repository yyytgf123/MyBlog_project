variable "public_subnet_id" {
  type = string
}

variable "private_subnet_id" {
  type = string
}

variable "bastion_security_group_id" {
  type = string
}

variable "private_security_group_id" {
  type = string
}

variable "eks_workernode_role" {
  type = string
}