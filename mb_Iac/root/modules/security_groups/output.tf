output "bastion_security_group_id" {
  value = aws_security_group.mb_bastion_security_group.id
}

output "private_security_group_id" {
  value = aws_security_group.mb_private_security_group.id
}

output "rds_security_group" {
  value = aws_security_group.mb_rds_security_group.id
}