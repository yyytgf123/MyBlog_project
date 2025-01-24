terraform {
  required_providers {
    aws = {
      source  = "hashicorp/aws"
      version = "5.54.1"
    }
  }
}

/*----- eks cluster -----*/
resource "aws_eks_cluster" "mb_eks_cluster" {
  name     = "mb_eks_cluster"

  role_arn = var.eks_cluster_role
  version = "1.31"

  vpc_config {
    subnet_ids = var.eks_private_subnet_ids
  }
}
/*--------------------------*/

/*----- eks worker node -----*/
# resource "aws_eks_node_group" "mb_eks_node_group" {
#   cluster_name  = aws_eks_cluster.mb_eks_cluster.name
#   node_role_arn = var.eks_workernode_role
#   subnet_ids = var.eks_private_subnet_ids
#
#   scaling_config {
#     desired_size = 1
#     max_size     = 3
#     min_size     = 1
#   }
#
#   ami_type = "ubuntu/images/hvm-ssd/ubuntu-jammy-22.04-amd64-server-*"
#   disk_size = "30"
#   instance_types = ["t2.micro"]
# }
/*------------------------------*/