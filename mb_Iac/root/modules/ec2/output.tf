output "bastion_ec2_ip" {
  value = aws_eip.mb_ec2_eip.id
}

output "private_ec2_ip" {
  value = aws_eip.mb_private_ec2.public_ip
}