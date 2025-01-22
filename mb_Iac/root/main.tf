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
}

module "ec2" {
  source = "./modules/ec2"
  security_group_id = module.security_groups.security_group_id
  private_subnet_id = module.vpc.private_subnet_ids
}