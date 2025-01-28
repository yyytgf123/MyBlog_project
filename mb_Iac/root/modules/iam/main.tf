terraform {
  required_providers {
    aws = {
      source  = "hashicorp/aws"
      version = "5.54.1"
    }
    random = {
      source  = "hashicorp/random"
      version = "3.6.2"
    }
  }
}

resource "random_string" "suffix" {
  length = 6
  special = false
  upper = false
}

/*----- eks cluster iam role -----*/
resource "aws_iam_role" "mb_eks_cluster_role" {
  name = "mb_eks_cluster_role-${random_string.suffix.result}"

  assume_role_policy = jsonencode({
    Version = "2012-10-17"
    Statement = [
      {
        Effect = "Allow"
        Principal = {
          Service = "eks.amazonaws.com"
        }
        Action = "sts:AssumeRole"
      },
    ]
  })
}
/*----------------------------------*/

/*----- eks cluster iam role policy -----*/
resource "aws_iam_role_policy_attachment" "mb_eks_cluster_policy" {
  for_each = toset(var.cluster_policy)
  policy_arn = each.key

  role = aws_iam_role.mb_eks_cluster_role.name
}
/*---------------------------------------*/

/*----- eks worker node iam role -----*/
resource "aws_iam_role" "mb_eks_workernode_role" {
  name = "mb_eks_workernode_role-${random_string.suffix.result}"

  assume_role_policy = jsonencode({
    Version = "2012-10-17"
    Statement = [
      {
        Effect = "Allow"
        Principal = {
          Service = "ec2.amazonaws.com"
        }
        Action = "sts:AssumeRole"
      },
    ]
  })
}
/*----- eks workder node iam olre policy ------*/
resource "aws_iam_role_policy_attachment" "mb_eks_workernode_policy" {
  for_each = toset(var.workdernode_policy)
  policy_arn = each.key

  role = aws_iam_role.mb_eks_workernode_role.name
}
/*----------------------------------------------*/

