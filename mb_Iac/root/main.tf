terraform {
  required_providers {
    aws = {
      source  = "hashicorp/aws"
      version = "5.54.1"
    }
  }
}

provider "aws" {
  region = "ap-northeast-2"
}

module "vpc" {
  source = "./modules/vpc" //해당 리렉토리 값을 참조 or 해당 디렉토리로 값을 전달
}

module "security_groups" {
  source = "./modules/security_groups"
  vpc_id = module.vpc.vpc_id //값을 참조할 디렉토리에서의 variable 이름 = module + module 명 + module variable
  bastion_ec2_ip = module.ec2.bastion_ec2_ip
}

module "ec2" {
  source = "./modules/ec2"
  bastion_security_group_id = module.security_groups.bastion_security_group_id
  private_security_group_id = module.security_groups.private_security_group_id
  public_subnet_id = module.vpc.public_subnet_id
  private_subnet_id = module.vpc.private_subnet_id
  eks_workernode_role = module.iam.eks_workernode_role
}

# module "route53" {
#   source = "./modules/route53"
#   bastion_ec2_ip = module.ec2.bastion_ec2_ip
#   # private_ec2_ip = module.ec2.private_ec2_ip
# }

module "iam" {
  source = "./modules/iam"
}

module "eks" {
  source = "./modules/eks"
  vpc_id = module.vpc.vpc_id
  eks_private_subnet_ids = module.vpc.eks_private_subnet_ids
  eks_cluster_role = module.iam.eks_cluster_role
  eks_workernode_role = module.iam.eks_workernode_role
}

module "asg" {
  source = "./modules/asg"
  mb_ec2_launch_template = module.ec2.mb_ec2_launch_template
  mb_eks_cluster = module.eks.mb_eks_cluster
  eks_private_subnet_ids = module.vpc.eks_private_subnet_ids
  mb_alb_tg = module.alb.mb_alb_tg
}

module "alb" {
  source = "./modules/alb"
  vpc_id = module.vpc.vpc_id
  alb_security_group_id = module.security_groups.alb_security_group
  public_subnet_ids = module.vpc.public_subnet_ids
}