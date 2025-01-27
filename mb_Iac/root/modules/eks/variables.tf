/* -- vpc -- */
variable "vpc_id" {
  type = string
}

variable "eks_private_subnet_ids" {
  type = list(string)
}

/* -- iam -- */
variable "eks_cluster_role" {
  type = string
}

variable "eks_workernode_role" {
  type = string
}

variable "eks_workernode_role_arn" {
  type = string
}

