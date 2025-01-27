terraform {
  required_providers {
    aws = {
      source  = "hashicorp/aws"
      version = "5.54.1"
    }
    null = {
      source  = "hashicorp/null"
      version = "3.2.2"
    }
  }
}

/*----- eks cluster -----*/
resource "aws_eks_cluster" "mb_eks_cluster" {
  name     = "mb_eks_cluster"

  role_arn = var.eks_cluster_role
  version = "1.32"

  vpc_config {
    subnet_ids = var.eks_private_subnet_ids
  }

  access_config {
    authentication_mode = "API_AND_CONFIG_MAP"
    bootstrap_cluster_creator_admin_permissions = true
  }
}
/*--------------------------*/

/*----- eks worker node -----*/
resource "aws_eks_node_group" "mb_eks_node_group" {
  cluster_name  = aws_eks_cluster.mb_eks_cluster.name
  node_role_arn = var.eks_workernode_role_arn
  subnet_ids = var.eks_private_subnet_ids

  scaling_config {
    desired_size = 1
    max_size     = 3
    min_size     = 1
  }

  ami_type = "AL2_x86_64"

  disk_size = "30"
  instance_types = ["t2.micro"]
}
/*------------------------------*/
resource "null_resource" "update_kubeconfig" {
  provisioner "local-exec" {
    command = "aws eks update-kubeconfig --region ap-northeast-2 --name ${aws_eks_cluster.mb_eks_cluster.name}"
  }
  depends_on = [aws_eks_cluster.mb_eks_cluster]
}