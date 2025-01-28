#eks cluser name
variable "cluster-name" {
  type = string
  default = "mb_eks_cluster"
}

variable "create_cluster" {
  description = "if true, create cluster, else not"
  type        = bool
  default     = true
}
variable "create_rds" {
  description = "if true, create rds, else not"
  type        = bool
  default     = false
}