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
  /* -- vpc -- */
  vpc_id = module.vpc.vpc_id //값을 참조할 디렉토리에서의 variable 이름 = module + module 명 + module variable
  /* -- ec2 -- */
  bastion_ec2_ip = module.ec2.bastion_ec2_ip
}

module "ec2" {
  source = "./modules/ec2"
  /* -- vpc -- */
  public_subnet_id = module.vpc.public_subnet_id
  private_subnet_id = module.vpc.private_subnet_id
  /* -- sg -- */
  bastion_security_group_id = module.security_groups.bastion_security_group_id
  private_security_group_id = module.security_groups.private_security_group_id
  /* -- iam -- */
  eks_workernode_role = module.iam.eks_workernode_role
  /* -- eks -- */
  eks_cluster_name = module.eks.mb_eks_cluster_name
}

module "route53" {
  source = "./modules/route53"
  /* -- ec2 -- */
  bastion_ec2_ip = module.ec2.bastion_ec2_ip
  /* -- alb -- */
  alb_dn = module.alb.mb_alb_dn
  alb_zone_id = module.alb.mb_alb_zond_id
}

module "iam" {
  source = "./modules/iam"
}

module "eks" {
  source = "./modules/eks"
  /* -- vpc -- */
  vpc_id = module.vpc.vpc_id
  eks_private_subnet_ids = module.vpc.eks_private_subnet_ids
  /* -- iam -- */
  eks_cluster_role = module.iam.eks_cluster_role
  eks_workernode_role = module.iam.eks_workernode_role
  eks_workernode_role_arn = module.iam.eks_workernode_role_arn
}

# module "asg" {
#   source = "./modules/asg"
#   /* -- vpc -- */
#   eks_private_subnet_ids = module.vpc.eks_private_subnet_ids
#   /* -- ec2 -- */
#   mb_ec2_launch_template = module.ec2.mb_ec2_launch_template
#   /* -- eks --*/
#   mb_eks_cluster = module.eks.mb_eks_cluster_name
#   /* -- alb --  */
#   mb_alb_tg = module.alb.mb_alb_tg
# }

module "alb" {
  source = "./modules/alb"
  /* -- vpc -- */
  vpc_id = module.vpc.vpc_id
  public_subnet_ids = module.vpc.public_subnet_ids
  /* -- alb -- */
  alb_security_group_id = module.security_groups.alb_security_group
}